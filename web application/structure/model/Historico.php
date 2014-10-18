<?php

	/**
	 * Classe Historico
	 * @license Thiago José Silveira
	 * @author Thiago José Silveira <blind.nomad@gmail.com>
	 * @dataProvider 29/07/2014 / 15:01:45
	 * @version 1.0
	 * @table(historico)
	 */
	class Historico extends Hypersistence{

		/**
		 * ID do Objeto
		 * @var int 
		 * @primaryKey
		 * @column(id)
		 */
		private $id;
		
		/**
		 * Veiculo que registrou o histórico
		 * @var Veiculo
		 * @manyToOne(lazy)
		 * @column(id_veiculo) 
		 * @itemClass(Veiculo)
		 */
		private $veiculo;
		
		/**
		 * Pessoa que registrou o histórico
		 * @var Pessoa
		 * @manyToOne(lazy)
		 * @column(id_pessoa)
		 * @itemClass(Pessoa)
		 */
		private $pessoa;
		
		/**
		 * Data do registro
		 * @var Data
		 * @column(data_hora) 
		 */
		private $dataHora;
		
		/**
		 * Tipo de acesso
		 * @var string
		 * @column(tipo_acesso) 
		 */
		private $tipoAcesso;
		
		public function getId() {
			return $this->id;
		}

		public function getVeiculo() {
			return $this->veiculo;
		}

		public function getPessoa() {
			return $this->pessoa;
		}

		public function getDataHora() {
			return $this->dataHora;
		}

		public function setId($id) {
			$this->id = $id;
		}

		public function setVeiculo(Veiculo $veiculo) {
			$this->veiculo = $veiculo;
		}

		public function setPessoa(Pessoa $pessoa) {
			$this->pessoa = $pessoa;
		}

		public function setDataHora(Data $dataHora) {
			$this->dataHora = $dataHora->getDataHora();
		}

		public function getTipoAcesso() {
			return $this->tipoAcesso;
		}

		public function setTipoAcesso($tipoAcesso) {
			$this->tipoAcesso = $tipoAcesso;
		}

		
	}

?>
