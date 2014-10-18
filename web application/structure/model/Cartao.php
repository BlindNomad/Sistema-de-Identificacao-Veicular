<?php

	/**
	 * Classe Cartao
	 * @license Thiago José Silveira
	 * @author Thiago José Silveira <blind.nomad@gmail.com>
	 * @dataProvider 17/07/2014 / 15:48:17
	 * @version 1.0
	 * @table(cartao)
	 */
	class Cartao extends Hypersistence{

		/**
		 * ID do Objeto
		 * @var int 
		 * @primaryKey
		 * @conlumn(id)
		 */
		private $id;
		/**
		 * Serial do cartão de acesso
		 * @var string 
		 * @column()
		 */
		private $serial;

		function __construct($id = null) {
			$this->id = $id;
			
		}

		public function getId() {
			return $this->id;
		}

		public function getSerial() {
			return $this->serial;
		}

		public function setId($id) {
			$this->id = $id;
		}

		public function setSerial($serial) {
			$this->serial = $serial;
		}

		public function loadCartao(){
			$buscaCartao = $this->search();
			$vetorCartao = $buscaCartao->execute();
			
			if ($buscaCartao->getTotalRows() == 1) {
				$this->id = $vetorCartao[0]->getId();
				$this->load();
				return true;
			}
			return false;
		}
	}

?>
