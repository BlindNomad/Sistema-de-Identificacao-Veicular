<?php

	/**
	 * Classe Condutor
	 * @license Thiago José Silveira
	 * @author Thiago José Silveira <blind.nomad@gmail.com>
	 * @dataProvider 17/07/2014 / 15:14:23
	 * @version 1.0
	 * @table(condutor)
	 * @joinColumn(id)
	 */
	class Condutor extends Pessoa {

		/**
		 * Numero da carteira de motorista.
		 * @var string 
		 * @column(numero_carteira)
		 */
		private $numeroCarteira;

		/**
		 * Validade da carteira de motorista
		 * @var Data 
		 * @column()
		 */
		private $validade;

		/**
		 * Se o condutor está liberado ou não
		 * @var int
		 * @column() 
		 */
		private $liberado;

		/**
		 * Numero do cartão de identificação
		 * @var Cartao 
		 * @column(id_cartao)
		 * @manyToOne(eager)
		 * @itemClass(Cartao)
		 */
		private $cartao;

		/**
		 * Veiculos atrelados ao condutor
		 * @var array|Veiculo
		 * @manyToMany(lazy)
		 * @joinTable(veiculo_has_condutor)
		 * @joinColumn(condutor_id)
		 * @inverseJoinColumn(veiculo_id)
		 * @itemClass(Veiculo)
		 */
		private $veiculos;

		/**
		 * Dias de validade da informação.
		 * @var int 
		 * @column(validade_informacao)
		 */
		private $validadeInformacao;

		/**
		 * Classes atreladas ao condutor
		 * @var Classe
		 * @manyToMany(lazy)
		 * @joinTable(classe_has_condutor)
		 * @joinColumn(condutor_id)
		 * @inverseJoinColumn(classe_id)
		 * @itemClass(Classe) 
		 */
		private $classes;

		/**
		 * Pontos na carteira de motorista
		 * @var int 
		 * @column(pontos_carteira)
		 */
		private $pontosCarteira;

		function __construct($id = null) {
			parent::__construct($id);
		}

		public function getNumeroCarteira() {
			return $this->numeroCarteira;
		}

		public function getValidade() {
			return $this->validade;
		}

		public function setNumeroCarteira($numeroCarteira) {
			$this->numeroCarteira = $numeroCarteira;
		}

		public function setValidade($validade) {
			$dateTime = new Data($validade);
			$this->validade = $dateTime;
		}

		public function getLiberado() {
			return $this->liberado;
		}

		public function setLiberado($liberado) {
			$this->liberado = $liberado;
		}

		public function getCartao() {
			return $this->cartao;
		}

		public function setCartao($cartao) {
			$this->cartao = $cartao;
		}

		public function addVeiculo(Veiculo $veiculo) {
			$this->addVeiculos($veiculo);
		}

		public function removeVeiculo(Veiculo $veiculo) {
			$this->deleteVeiculos($veiculo);
		}

		/**
		 * 
		 * @return ResultSet
		 */
		public function getVeiculos() {
			return $this->veiculos;
		}

		public function setVeiculos($veiculos) {
			$this->veiculos = $veiculos;
		}

		public function getValidadeInformacao() {
			return $this->validadeInformacao;
		}

		public function setValidadeInformacao($validadeInformacao) {
			$this->validadeInformacao = $validadeInformacao;
		}

		/**
		 * 
		 * @return array|Classe
		 */
		public function getClasses() {
			return $this->classes;
		}

		public function setClasses($classes) {
			$this->classes = $classes;
		}

		public function addClasse(Classe $classe) {
			$this->addClasses($classe);
		}

		public function removeClasse(Classe $classe) {
			$this->deleteClasses($classe);
		}

		public function getPontosCarteira() {
			return $this->pontosCarteira;
		}

		public function setPontosCarteira($pontosCarteira) {
			$this->pontosCarteira = $pontosCarteira;
		}

//		public function load($forceReload = false) {
//			parent::load($forceReload);
//		}
	}

?>
