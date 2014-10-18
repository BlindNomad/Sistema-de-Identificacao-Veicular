<?php

	/**
	 * Classe Pessoa
	 * @license Thiago José Silveira
	 * @author Thiago José Silveira <blind.nomad@gmail.com>
	 * @dataProvider 17/07/2014 / 15:14:11
	 * @version 1.0
	 * @table(pessoa)
	 */
	class Pessoa extends Hypersistence {

		/**
		 * ID do Objeto
		 * @var int 
		 * @primaryKey
		 * @column(id)
		 */
		protected $id;

		/**
		 * id da pessoa que cadastrou
		 * @var Pessoa
		 * @column(id_pessoa_cima)
		 * @manyToOne(lazy) 
		 * @itemClass(Pessoa)
		 */
		protected $PessoaCima;

		/**
		 * Nome da pessoa
		 * @var string
		 * @column() 
		 */
		protected $nome;

		/**
		 * CPF da pessoa
		 * @var string
		 * @column() 
		 */
		protected $cpf;

		/**
		 * Telefone da pessoa
		 * @var string
		 * @column() 
		 */
		protected $telefone;

		/**
		 * Endereço da pessoa
		 * @var string
		 * @column() 
		 */
		protected $endereco;

		/**
		 * Cidade da pessoa
		 * @var string
		 * @column() 
		 */
		protected $cidade;

		/**
		 * Estado da federação da pessoa
		 * @var string
		 * @column() 
		 */
		protected $estado;

		/**
		 * Usuário de login
		 * @var string
		 * @column() 
		 */
		protected $usuario;

		/**
		 * Senha de login
		 * @var string
		 * @column() 
		 */
		protected $senha;

		/**
		 * Permissão de usuário
		 * @var int 
		 * @column()
		 */
		protected $permissao;

		/**
		 * Histórico da pessoa
		 * @var array | Historico
		 * @oneToMany(lazy)
		 * @itemClass(Historico)
		 * @joinColumn(id_pessoa) 
		 */
		protected $historicos;

		function __construct($id = null) {
			$this->id = $id;
		}

		public function getId() {
			return $this->id;
		}

		public function getNome() {
			return $this->nome;
		}

		public function getCpf() {
			return $this->cpf;
		}

		public function getTelefone() {
			return $this->telefone;
		}

		public function getEndereco() {
			return $this->endereco;
		}

		public function getCidade() {
			return $this->cidade;
		}

		public function getEstado() {
			return $this->estado;
		}

		public function getUsuario() {
			return $this->usuario;
		}

		public function getSenha() {
			return $this->senha;
		}

		public function getPermissao() {
			return $this->permissao;
		}

		public function setId($id) {
			$this->id = $id;
		}

		public function setNome($nome) {
			$this->nome = $nome;
		}

		public function setCpf($cpf) {
			$this->cpf = $cpf;
		}

		public function setTelefone($telefone) {
			$this->telefone = $telefone;
		}

		public function setEndereco($endereco) {
			$this->endereco = $endereco;
		}

		public function setCidade($cidade) {
			$this->cidade = $cidade;
		}

		public function setEstado($estado) {
			$this->estado = $estado;
		}

		public function setUsuario($usuario) {
			$this->usuario = $usuario;
		}

		public function setSenha($senha) {
			$this->senha = sha1($senha);
		}
		
		public function setSenhaS($senha){
			$this->senha = $senha;
		}

		public function setPermissao($permissao) {
			$this->permissao = $permissao;
		}

		public function getHistoricos() {
			return $this->historicos;
		}

		public function setHistoricos($historicos) {
			$this->historicos = $historicos;
		}
		
		public function getPessoaCima() {
			return $this->PessoaCima;
		}

		public function setPessoaCima(Pessoa $PessoaCima) {
			$this->PessoaCima = $PessoaCima;
		}

	
	}

?>
