<?php

	/**
	 * Classe AutenticacaoController
	 * @license Thiago José Silveira
	 * @author Thiago José Silveira <blind.nomad@gmail.com>
	 * @dataProvider 16/08/2014 / 13:01:35
	 * @version 1.0
	 */
	class AutenticacaoController extends BasicController{
		
		public $permissao;

		public function indexAction() {
			
		}
		
		public function beforeAction() {
			parent::beforeAction();
			
			
			if (!isset($_SESSION['aut']) || !$_SESSION['aut'] || $_SESSION['nivel'] < 2 ){
				
				Request::redirect("/");
			}else{
				$this->permissao = $_SESSION['nivel'];
			}
			
			
		}
	}

?>
