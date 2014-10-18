<?php

	/**
	 * Classe InicialController
	 * @license http://www.gsurfnet.com GSurf
	 * @author Thiago José Silveira <blind.nomad@gmail.com>
	 * @dataProvider 14/07/2014 / 17:59:47
	 * @version 1.0
	 */
	class InicialController extends AutenticacaoController{

		public function indexAction() {
			
		}
		
		public function sairAction(){
			$_SESSION['aut'] = false;
			Session::destroy();
			Request::redirect("/");
		}

		public function __construct() {
			$this->templateName = 'templateInicial';
		}
	}

?>
