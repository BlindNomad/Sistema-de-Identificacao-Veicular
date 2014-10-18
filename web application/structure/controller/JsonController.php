<?php

	/**
	 * Classe JsonController
	 * @license Thiago José Silveira
	 * @author Thiago José Silveira <blind.nomad@gmail.com>
	 * @dataProvider 29/07/2014 / 18:58:20
	 * @version 1.0
	 */
	class JsonController extends BasicController {

		public function indexAction() {

			if (Request::isPost()) {
				$post = Request::$post;

				$dados = $post->dados;
				$json = json_decode($dados);
				switch ($json->codigo) {
					case 1: // Buscar todos.
						$historico = new Historico();
						$mensagem = new Mensagem();
						$veiculo = $this->pegarVeiculo($json->placa);
						if ($veiculo != false) {
							$historico->setVeiculo($veiculo);
							$data = new Data();
							$historico->setDataHora($data);
							$historico->setTipoAcesso("Veículo pegou dados dos condutores.");
							$historico->setVeiculo($veiculo);
							if ($historico->save()) {
								$historico->commit();
								$mensagem->codigo = 200;
								$condutores = $this->consultarCondutoresVeiculo(trim($json->placa));
								$arrayCondutores = array();
								for ($i = 0; $i < count($condutores); $i++) {
									$classes = $condutores[$i]->getClasses()->execute();
									foreach ($classes as $c) {
										if ($c->getClasse() == trim($json->classe)) {
											$arrayCondutores[$i]['nome'] = $condutores[$i]->getNome();
											$arrayCondutores[$i]['nCarteira'] = $condutores[$i]->getNumeroCarteira();
											$arrayCondutores[$i]['validade'] = $condutores[$i]->getValidade()->getDataHora();
											$arrayCondutores[$i]['pontos'] = $condutores[$i]->getPontosCarteira();
											$arrayCondutores[$i]['id'] = $condutores[$i]->getId();
											$arrayCondutores[$i]['liberado'] = $condutores[$i]->getLiberado();
											$arrayCondutores[$i]['cartao'] = $condutores[$i]->getCartao()->load()->getSerial();
											$arrayCondutores[$i]['tempoVida'] = $condutores[$i]->getValidadeInformacao();
											break;
										}
									}
								}
								$mensagem->mensagem = $arrayCondutores;
							}
						} else {
							$mensagem->codigo = 404;
						}

						echo json_encode($mensagem);
						exit();
						break;

					case 2: // Pega 1 condutor pelo numero do cartao.
						$historico = new Historico();
						$mensagem = new Mensagem();
						$veiculo = $this->pegarVeiculo($json->placa);
						if ($veiculo != false) {
							$historico->setVeiculo($veiculo);
							$data = new Data();
							$historico->setDataHora($data);
							$condutor = $this->pegaCondutorCartao($json->cartao);
							if ($condutor == false) {
								$historico->setTipoAcesso("Condutor não encontrado.");
								$mensagem->codigo = 404;
							} else {
								$historico->setTipoAcesso("Veículo pegou dados do Condutor.");
								$arrayCondutor['nome'] = $condutor->getNome();
								$arrayCondutor['nCarteira'] = $condutor->getNumeroCarteira();
								$arrayCondutor['validade'] = $condutor->getValidade()->getDataHora();
								$arrayCondutor['pontos'] = $condutor->getPontosCarteira();
								$arrayCondutor['id'] = $condutor->getId();
								$arrayCondutor['liberado'] = $condutor->getLiberado();
								$arrayCondutor['cartao'] = $condutor->getCartao()->load()->getSerial();
								$arrayCondutor['tempoVida'] = $condutor->getValidadeInformacao();
								$historico->setVeiculo($veiculo);
								$pessoa = new Pessoa($condutor->getId());
								$historico->setPessoa($pessoa);
								if ($historico->save()) {
									$historico->commit();
								}
								$mensagem->codigo = 201;
								$mensagem->mensagem = $arrayCondutor;
							}
							echo json_encode($mensagem);
							exit();
							break;
						}


					case 3:

						$mensagem = new Mensagem();
						$pessoa = new Pessoa();
//						var_dump($json->usuario);
						$pessoa->setUsuario($json->usuario);
						$pessoa->setSenhaS($json->senha);
						$buscaUsuario = $pessoa->search();
						$vetorUsuario = $buscaUsuario->execute();
						if (count($vetorUsuario) == 1) {
							$condutor = new Condutor($vetorUsuario[0]->getId());
							$condutor->load();
							$veiculos = $condutor->getVeiculos()->execute();
							$placas = array();
							foreach ($veiculos as $v) {
								$placas[] = $v->getPlaca();
							}
							
							$mensagem->codigo = 300;
							$mensagem->mensagem = $placas;
						} else {
							$mensagem->codigo = 404;
							$mensagem->mensagem = 0;
						}


						echo json_encode($mensagem);
						exit();
						break;

					default:
						$mensagem = new Mensagem();
						$mensagem->codigo = 405;
						echo json_encode($mensagem);
						exit();
						break;
				}
			}
			exit();
		}

		/**
		 * 
		 * @param string $placa
		 * @return array|Condutor
		 */
		private function consultarCondutoresVeiculo($placa) {
			$veiculo = new Veiculo();
			$veiculo->setPlaca($placa);
			$buscaVeiculo = $veiculo->search();
			$vetorVeiculo = $buscaVeiculo->execute();
			if ($buscaVeiculo->getTotalRows() == 1) {
				$veiculo = $vetorVeiculo[0];
				$veiculo->load();
				$condutores = $veiculo->getCondutores()->execute();
				return $condutores;
			}
		}

		/**
		 * 
		 * @param string $placa
		 * @return boolean|Veiculo
		 */
		private function pegarVeiculo($placa) {
			$veiculo = new Veiculo();
			$veiculo->setPlaca($placa);
			$buscaVeiculo = $veiculo->search();
			$vetorVeiculo = $buscaVeiculo->execute();
			if ($buscaVeiculo->getTotalRows() == 1) {
				$veiculo = $vetorVeiculo[0];
				$veiculo->load();
				return $veiculo;
			}

			return false;
		}

		/**
		 * Pega um condutor pelo numero do cartao;
		 * @param string $cartao
		 * @return boolean|Condutor
		 */
		private function pegaCondutorCartao($serial) {
			$condutor = new Condutor();
			$cartao = new Cartao();
			$cartao->setSerial($serial);
			if (!$cartao->loadCartao()) {
				return false;
			}

			$condutor->setCartao($cartao->getId());
			$buscaCondutor = $condutor->search();
			$vetorCondutor = $buscaCondutor->execute();

			if ($buscaCondutor->getTotalRows() == 1) {
				$condutor = $vetorCondutor[0];
				$condutor->load();
				return $condutor;
			}

			return false;
		}

	}

?>
