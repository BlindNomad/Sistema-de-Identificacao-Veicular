<?php

	/**
	 * Classe CadastroCondutor
	 * @license Thiago José Silveira
	 * @author Thiago José Silveira <blind.nomad@gmail.com>
	 * @dataProvider 15/07/2014 / 16:48:33
	 * @version 1.0
	 */
	class CondutorController extends AutenticacaoController {

		public $salvo;
		public $cpfS;
		public $cartaoS;
		public $veiculo;
		public $erroVeiculo;
		public $veiculos;
		public $menu;
		public $vetorClasse;

		/**
		 *
		 * @var Condutor 
		 */
		public $condutor;

		public function indexAction() {
			
		}

		public function cadastroAction() {

			$this->salvo = false;

			if (Request::isPost()) {
				$post = Request::$post;

				if (isset($post->cadastro)) {
					$nome = $post->nome;
					$cpf = $post->cpf;
					$telefone = $post->tel;
					$endereco = $post->end;
					$cidade = $post->cidade;
					$estado = $post->uf;
					$usuario = $post->usuario;
					$senha = $post->senha;
					$permissao = $_SESSION['nivel'] == 3 ? 2 : 0;
					$pessoaAcima = $_SESSION['nivel'] == 3 ? null : new Pessoa($_SESSION['id']);
					$numeroCarteira = $post->ncarteira;
					$validade = $post->validade;
					$condutor = new Condutor();
					if (isset($post->id) && $post->id != 0) {
						$condutor->setId($post->id);
						$condutor->load();
					}
					$condutor->setNome($nome);
					$condutor->setCpf($cpf);
					$condutor->setTelefone($telefone);
					$condutor->setEndereco($endereco);
					$condutor->setCidade($cidade);
					$condutor->setEstado($estado);
					$condutor->setUsuario($usuario);
					$condutor->setSenha($senha);
					$condutor->setPermissao($permissao);
					$condutor->setNumeroCarteira($numeroCarteira);
					$condutor->setValidade($validade);
					$condutor->setPermissao($permissao);
					if (!is_null($pessoaAcima))
						$condutor->setPessoaCima($pessoaAcima);
					if (isset($post->liberado)) {
						$condutor->setLiberado(1);
					} else {
						$condutor->setLiberado(0);
					}

					$condutor->setValidadeInformacao(10);
					$condutor->setPontosCarteira(0);
					if ($condutor->save()) {
						$this->salvo = true;
						$condutor->commit();
					} else {
						echo "Erro";
						//var_dump(DB::getDBConnection()->errorInfo());
					}
				}
			}

			if (Request::$get) {
				$get = Request::$get;

				if (isset($get->id)) {
					$this->condutor = new Condutor($get->id);
					$this->condutor->load();
				}
			}
		}

		public function consultarAction() {
			if (Request::isPost()) {
				$post = Request::$post;

				if (isset($post->buscar)) {

					$condutor = new Condutor();
					$condutor->setCpf($post->cpf);
					$buscaCondutor = $condutor->search();
					$vetorCondutor = $buscaCondutor->fetchAll();
					if ($buscaCondutor->getTotalRows() == 1) {

						$condutor = $vetorCondutor[0];
						$condutor->load();
						$this->veiculos = $condutor->getVeiculos()->orderBy("cor", "asc")->execute();
					} else {
						$this->cpfS = true;
					}
				}

				if (isset($post->editar)) {
					Request::redirect("/veiculo/cadastro?id=" . $post->editar);
				}
			}
		}

		public function atribuirAction() {
			$this->salvo = false;
			$this->cpfS = false;
			$this->cartaoS = false;
			if (Request::isPost()) {
				$post = Request::$post;
				if (isset($post->cadastro)) {
					$nCpf = $post->cpf;
					$nCartao = $post->ncart;
					$condutor = new Condutor();
					$cartao = new Cartao();

					$condutor->setCpf($nCpf);
					$cartao->setSerial($nCartao);

					$buscaCartoes = $cartao->search();
					$buscaCondutores = $condutor->search();
					$vetorCartoes = $buscaCartoes->fetchAll();
					$vetorCodutores = $buscaCondutores->execute();


					if ($buscaCartoes->getTotalRows() == 1) {
						//echo $buscaCondutores->getTotalRows();
						if ($buscaCondutores->getTotalRows() == 1) {
							$condutorCartao = new Condutor();
							$condutorCartao->setCartao($vetorCartoes[0]->getSerial());
							$buscaCartoesUsados = $condutorCartao->search();

							if ($buscaCartoesUsados->getTotalRows() == 0) {
								$condutor->setId($vetorCodutores[0]->getId());
								$condutor->load();

								$condutor->setCartao($vetorCartoes[0]);
								if ($condutor->save()) {
									$condutor->commit();
									$this->salvo = true;
								} else {
									
								}
							} else {
								$this->cartaoS = true;
							}
						} else {
							$this->cpfS = true;
						}
					} else {
						$this->cartaoS = true;
					}
				}
			}
		}

		public function atribuirVeiculoAction() {
			$this->salvo = false;
			$this->cpfS = false;
			$this->veiculo = false;
			$this->erroVeiculo = false;
			if (Request::isPost()) {
				$post = Request::$post;
				if (isset($post->cadastro)) {
					$nCpf = $post->cpf;
					$nPlaca = $post->placa;
					$condutor = new Condutor();
					$veiculo = new Veiculo();
					$condutor->setCpf($nCpf);
					$veiculo->setPlaca($nPlaca);
					$buscaCondutor = $condutor->search();
					$buscaVeiculo = $veiculo->search();
					$vetorCondutor = $buscaCondutor->fetchAll();
					$vetorVeiculo = $buscaVeiculo->fetchAll();

					if ($buscaCondutor->getTotalRows() == 1) {
						if ($buscaVeiculo->getTotalRows() == 1) {

							$condutor = $vetorCondutor[0];
							$veiculo = $vetorVeiculo[0];
							$condutor->addVeiculo($veiculo);

							$habilitadoCad = false;

							if ($_SESSION['nivel'] != 3) {
								$responsavel = new Condutor($_SESSION['id']);
								$responsavel->load();
								$habilitadoCad = $this->responsavel($responsavel, $veiculo);
							} else {
								$habilitadoCad = true;
							}

							if ($condutor->save() && $habilitadoCad) {
								$condutor->commit();
								$this->salvo = true;
							} else {
								$this->erroVeiculo = true;
							}
						} else {
							$this->veiculo = true;
						}
					} else {
						$this->cpfS = true;
					}
				}
			}
		}

		public function atribuirClasseAction() {
			$this->salvo = false;
			if (Request::isPost()) {
				$post = Request::$post;
				if (isset($post->cadastro)) {
					$condutor = new Condutor();
					$condutor->setCpf($post->cpf);
					$buscarCondutores = $condutor->search();
					$vetorCondutores = $buscarCondutores->execute();
					if ($buscarCondutores->getTotalRows() == 1) {

						$condutor = $vetorCondutores[0];

						$classe = new Classe($post->classe);
						$classe->load();
						$condutor->addClasse($classe);

						$autorizado = FALSE;
						if ($_SESSION['nivel'] != 3) {
							$condutor->load();
							if (!is_null($condutor->getPessoaCima())){
								$pessoaAcima = $condutor->getPessoaCima()->load();
								$idPessoaAcima = $pessoaAcima->getId();
							}else{
								$idPessoaAcima = 0;
							}
							
							if ($idPessoaAcima == $_SESSION['id']) {
								$autorizado = true;
							}
						} else {
							$autorizado = true;
						}

						if ($condutor->save() && $autorizado) {
							$condutor->commit();
							$this->salvo = true;
						} else {
							$this->cpfS = true;
						}
					} else {
						$this->cpfS = true;
					}
				}
			}

			$classe = new Classe();
			$buscaClasse = $classe->search();
			$this->vetorClasse = $buscaClasse->fetchAll();
		}

		function __construct() {
			$this->menu = 2;
			$this->templateName = 'templateInicial';
			$this->condutor = new Condutor();
		}

		private function responsavel(Condutor $responsavel, Veiculo $veiculo) {
			$v = $veiculo;
			$respVeiculos = $responsavel->getVeiculos()->execute();
			foreach ($respVeiculos as $r) {
				if ($r->getId() == $v->getId()) {
					return true;
				}
			}
			return false;
		}

		private function verificaADM() {
			if ($_SESSION['nivel'] != 3) {
				$_SESSION['aut'] = false;
				Request::redirect("/");
			}
		}

	}

?>
