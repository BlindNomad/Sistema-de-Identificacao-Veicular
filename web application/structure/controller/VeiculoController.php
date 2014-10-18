<?php

	/**
	 * Classe VeiculoController
	 * @license Thiago José Silveira
	 * @author Thiago José Silveira <blind.nomad@gmail.com>
	 * @dataProvider 19/07/2014 / 12:19:12
	 * @version 1.0
	 */
	class VeiculoController extends AutenticacaoController {

		public $vetorClasse;
		public $salvo;
		public $cpfS;
		public $placaS;
		public $menu;

		/**
		 *
		 * @var array|Condutor
		 */
		public $condutores;

		/**
		 *
		 * @var Veiculo 
		 */
		public $veiculo;

		public function cadastroAction() {

			if (Request::isPost()) {
				$post = Request::$post;
				if (isset($post->cadastro)) {
					$classeId = $post->classe;
					$placa = $post->placa;
					$cor = $post->cor;
					$renavam = $post->ren;
					$classe = new Classe($classeId);
					$veiculo = new Veiculo();
					$veiculo->setClasse($classe);
					$veiculo->setPlaca($placa);
					$veiculo->setCor($cor);
					$veiculo->setRenavam($renavam);
					if ($veiculo->save()) {
						$veiculo->commit();
						$this->salvo = true;
					}
				}
			}

			$classe = new Classe();
			$buscaClasse = $classe->search();
			$this->vetorClasse = $buscaClasse->fetchAll();
		}

		public function atribuirAction() {
			$this->salvo = false;
			$this->cpfS = false;
			$this->placaS = false;
			if (Request::isPost()) {
				$post = Request::$post;
				if (isset($post->cadastro)) {
					$nCpf = $post->cpf;
					$nPlaca = $post->placa;
					$condutor = new Condutor();
					$veiculo = new Veiculo;
					$condutor->setCpf($nCpf);
					$veiculo->setPlaca($nPlaca);
					$buscaCondutores = $condutor->search();
					$buscaVeiculos = $veiculo->search();
					$vetorCondutores = $buscaCondutores->execute();
					$vetorVeiculos = $buscaVeiculos->execute();

					if ($buscaVeiculos->getTotalRows() == 1) {

						if ($buscaCondutores->getTotalRows() == 1) {

							$veiculo = $vetorVeiculos[0];
							$this->condutores = array();
							
							if (count($veiculo->getCondutores()->getResultList()) > 0) {
								$this->condutores = $veiculo->getCondutores()->excute();
							}

							$cancelaConsulta = $this->cancelarConsulta();
							if (!$cancelaConsulta) {
								$veiculo->addCondutor($vetorCondutores[0]);
								if ($veiculo->save()) {
									$veiculo->commit();
									$this->salvo = true;
								}
							} else {
								$this->placaS = true;
							}
						} else {
							$this->cpfS = true;
						}
					} else {
						$this->placaS = true;
					}
				}
				if (Request::$get) {
					$get = Request::$get;

					if (isset($get->id)) {
						$this->veiculo = new Veiculo($get->id);
						$this->veiculo->load();
					}
				}
			}
		}

		public function consultarAction() {
			if (Request::isPost()) {
				$post = Request::$post;
				if (isset($post->buscar)) {
					$veiculo = new Veiculo();
					$veiculo->setPlaca($post->placa);
					$buscaVeiculo = $veiculo->search();
					$vetorVeiculo = $buscaVeiculo->execute();
					if ($buscaVeiculo->getTotalRows() == 1) {
						$this->veiculo = $vetorVeiculo[0];
						$this->veiculo->load();
						$this->condutores = $this->veiculo->getCondutores()->execute();
						$cancelarConsulta = $this->cancelarConsulta();

						if ($cancelarConsulta) {
							$this->condutores = array();
						}
					}
				}

				if (isset($post->editar)) {
					Request::redirect("/condutor/cadastro?id=" . $post->editar);
				}

				if (isset($post->excluir)) {
					$condutor = new Condutor($post->excluir);
					$condutor->load();
					$veiculo = new Veiculo($post->veiculoId);
					$veiculo->load();
					$condutor->removeVeiculo($veiculo);
					if ($condutor->save()) {
						$condutor->commit();
					}
				}
			}
		}

		private function cancelarConsulta() {
			
			if ($_SESSION['nivel'] < 3) {
				foreach ($this->condutores as $c) {
					if ($c->getId() == $_SESSION['id']) {
						return false;
					}
				}
			} else {
				return false;
			}
			return true;
		}

		function __construct() {
			$this->menu = 1;
			$this->templateName = 'templateInicial';
		}

		public function indexAction() {
			
		}

	}

?>
