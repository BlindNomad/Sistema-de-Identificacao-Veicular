<?php

	/**
	 * Classe Classe
	 * @license Thiago José Silveira
	 * @author Thiago José Silveira <blind.nomad@gmail.com>
	 * @dataProvider 17/07/2014 / 15:50:39
	 * @version 1.0
	 * @table(classe)
	 */
	class Classe extends Hypersistence {

		/**
		 * ID do Objeto
		 * @var int 
		 * @primaryKey
		 * @column(id)
		 */
		private $id;

		/**
		 * Tipo de classe do automóvel ou condutor
		 * @var string
		 * @column() 
		 */
		private $classe;

		/**
		 * Lista de condutores que contém essa classe
		 * @var array|Condutor
		 * @manyToMany(lazy)
		 * @joinTable(classe_has_condutor)
		 * @joinColumn(classe_id)
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

		public function setId($id) {
			$this->id = $id;
		}

		public function setClasse($classe) {
			$this->classe = $classe;
		}

		public function getCondutores() {
			return $this->condutores;
		}

		public function setCondutores($condutores) {
			$this->condutores = $condutores;
		}

	}

?>
