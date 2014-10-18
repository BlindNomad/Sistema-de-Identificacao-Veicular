<?php

	/**
	 * Classe Veiculo
	 * @license Thiago José Silveira
	 * @author Thiago José Silveira <blind.nomad@gmail.com>
	 * @dataProvider 17/07/2014 / 16:27:45
	 * @version 1.0
	 * @table(veiculo)
	 */
	class Veiculo extends Hypersistence {

		/**
		 * ID do Objeto
		 * @var int 
		 * @primaryKey
		 * @column(id)
		 */
		private $id;

		/**
		 * Classe do veiculo
		 * @var Classe
		 * @manyToOne(eager)
		 * @column(id_classe) 
		 * @itemClass(Classe)
		 */
		private $classe;

		/**
		 * Placa do veiculo
		 * @var string
		 * @column() 
		 */
		private $placa;

		/**
		 * Cor do veiculo
		 * @var string
		 * @column() 
		 */
		private $cor;

		/**
		 * Renavam do veiculo
		 * @var string 
		 * @column()
		 */
		private $renavam;

		/**
		 * Condutores para este veiculo
		 * @var array|Condutor
		 * @manyToMany(lazy)
		 * @joinTable(veiculo_has_condutor)
		 * @joinColumn(veiculo_id) 
		 * @inverseJoinColumn(condutor_id)
		 * @itemClass(Condutor)
		 */
		private $condutores;

		function __construct($id = null) {
			$this->id = $id;
		}

		public function getId() {
			return $this->id;
		}

		public function getClasse() {
			
			return $this->classe;
		}

		public function getPlaca() {
			return $this->placa;
		}

		public function getCor() {
			return $this->cor;
		}

		public function getRenavam() {
			return $this->renavam;
		}

		public function setId($id) {
			$this->id = $id;
		}

		public function setClasse(Classe $classe) {
			$this->classe = $classe;
		}

		public function setPlaca($placa) {
			$this->placa = $placa;
		}

		public function setCor($cor) {
			$this->cor = $cor;
		}

		public function setRenavam($renavam) {
			$this->renavam = $renavam;
		}

		public function addCondutor(Condutor $condutor) {
			$this->addCondutores($condutor);
		}

		/**
		 * 
		 * @return Hypersistence\Core\QueryBuilder
		 */
		public function getCondutores() {
			return $this->condutores;
		}

		public function removeCondutor(Condutor $condutor) {
			$this->deleteCondutor($condutor);
		}

		public function setCondutores($condutores) {
			$this->condutores = $condutores;
		}

	}

?>
