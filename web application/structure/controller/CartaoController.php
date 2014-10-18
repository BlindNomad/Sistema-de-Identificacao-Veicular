<?php

	/**
	 * Classe CartaoController
	 * @license Thiago José Silveira
	 * @author Thiago José Silveira <blind.nomad@gmail.com>
	 * @dataProvider 18/07/2014 / 17:25:40
	 * @version 1.0
	 */
	class CartaoController extends AutenticacaoController {

		public $salvo;
		
		public function indexAction() {
			
		}
		
		public function cadastroAction(){
			
			if (Request::isPost()) {
				$post = Request::$post;
				if (isset($post->cadastro)) {
					$serial = $post->ncart;
					$cartao = new Cartao();
					$cartao->setSerial($serial);
					if ($cartao->save()) {
						$this->salvo = true;
						$cartao->commit();
					}
				}
			}
		}
		
		function __construct() {
			$this->templateName = 'templateInicial';
		}
	}

?>
