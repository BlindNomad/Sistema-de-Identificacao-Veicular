<?php
class Data{
	/**
	 * @var integer
	 */
	private $dia;
	/**
	 * @var integer
	 */
	private $mes;
	/**
	 * @var integer
	 */
	private $ano;
	/**
	 * @var integer
	 */
	private $hora;
	/**
	 * @var integer
	 */
	private $minuto;
	/**
	 * @var integer
	 */
	private $segundo;
	
	/**
	 * @param string $data
	 */
	public function __construct($data = ''){
		if($data != ''){
			$d = array(0, 0, 0);
			$h = array(0, 0, 0);
			if(preg_match('/[0-9]{4}\-[0-9]{2}\-[0-9]{2} [0-2][0-9]:[0-5][0-9]:[0-5][0-9]/', $data)){
				$d = explode(' ', $data);
				$h = explode(':', $d[1]);
				$d = explode('-', $d[0]);
			}else if(preg_match('/[0-9]{4}\-[0-9]{2}\-[0-9]{2}/', $data)){
				$d = explode('-', $data);
			}else if(preg_match('/[0-9]{4}\/[0-9]{2}\/[0-9]{2} [0-2][0-9]:[0-5][0-9]:[0-5][0-9]/', $data)){
				$d = explode(' ', $data);
				$h = explode(':', $d[1]);
				$d = explode('/', $d[0]);
			}else if(preg_match('/[0-9]{4}\/[0-9]{2}\/[0-9]{2}/', $data)){
				$d = explode('/', $data);
			}else if(preg_match('/[0-9]{2}\/[0-9]{2}\/[0-9]{4} [0-2][0-9]:[0-5][0-9]:[0-5][0-9]/', $data)){
				$d = explode(' ', $data);
				$h = explode(':', $d[1]);
				$d = array_reverse(explode('/', $d[0]));
			}else if(preg_match('/[0-9]{2}\/[0-9]{2}\/[0-9]{4}/', $data)){
				$d = array_reverse(explode('/', $data));
			}else if(preg_match('/[0-9]{1,2}\/[0-9]{1,2}\/[0-9]{2}/', $data)){
				$d = array_reverse(explode('/', $data));
				$d[0] = '20'.$d[0];
			}else if(preg_match('/[0-9]{1,2}\-[0-9]{1,2}\-[0-9]{2}/', $data)){
				$d = array_reverse(explode('-', $data));
				$d[0] = '20'.$d[0];
				$dia = $d[1];
				$d[1] = $d[2];
				$d[2] = $dia;
			}else if(preg_match('/[0-9]{10}/', $data)){
				$d = array(date('Y', $data), date('m', $data), date('d', $data));
				$h = array(date('H', $data), date('i', $data), date('s', $data));
			}

			$this->ano = $d[0];
			$this->mes = $d[1];
			$this->dia = $d[2];
			$this->hora = $h[0];
			$this->minuto = $h[1];
			$this->segundo = $h[2];
		}else{
			$this->ano = date('Y');
			$this->mes = date('m');
			$this->dia = date('d');
			$this->hora = date('H');
			$this->minuto = date('i');
			$this->segundo = date('s');
		}
	}
	
	

	/**
	 * retorna o dia.
	 * @return integer
	 */
	public function getDia()
	{
	    return $this->dia;
	}

	/**
	 * configura o dia.
	 * @param integer $dia
	 */
	public function setDia($dia)
	{
	    $this->dia = $dia;
	}

	/**
	 * retorna o mês.
	 * @return integer
	 */
	public function getMes()
	{
	    return $this->mes;
	}

	/**
	 * configura o mês.
	 * @param integer $mes
	 */
	public function setMes($mes)
	{
	    $this->mes = $mes;
	}

	/**
	 * retorna o ano.
	 * @return integer
	 */
	public function getAno()
	{
	    return $this->ano;
	}

	/**
	 * configura o ano.
	 * @param integer $ano
	 */
	public function setAno($ano)
	{
	    $this->ano = $ano;
	}

	/**
	 * retorna a hora.
	 * @return integer
	 */
	public function getHora()
	{
	    return $this->hora;
	}

	/**
	 * configura a hora.
	 * @param integer $hora
	 */
	public function setHora($hora)
	{
	    $this->hora = $hora;
	}

	/**
	 * retorna o minuto.
	 * @return integer
	 */
	public function getMinuto()
	{
	    return $this->minuto;
	}

	/**
	 * configura o minuto.
	 * @param integer $minuto
	 */
	public function setMinuto($minuto)
	{
	    $this->minuto = $minuto;
	}

	/**
	 * retorna o segundo.
	 * @return integer
	 */
	public function getSegundo()
	{
	    return $this->segundo;
	}

	/**
	 * configura o segundo.
	 * @param integer $segundo
	 */
	public function setSegundo($segundo)
	{
	    $this->segundo = $segundo;
	}
	
	/**
	 * retorna o timestamp.
	 * @return integer
	 */
	public function getTime(){
		return mktime($this->hora, $this->minuto, $this->segundo, $this->mes, $this->dia, $this->ano);
	}
	
	/**
	 * retorna a data no formato brasileiro (dd/mm/aaaa).
	 * @return string
	 */
	public function getDataBrasil(){
		return $this->dia.'/'.$this->mes.'/'.$this->ano;
	}
	
	/**
	 * retorna a data e hora no formato brasileiro (dd/mm/aaaa hh:mm:ss)
	 * @return string
	 */
	public function getDataHoraBrasil(){
		return $this->dia.'/'.$this->mes.'/'.$this->ano.' '.$this->hora.':'.$this->minuto.':'.$this->segundo;
	}
	
	/**
	 * retorna a data no formato internacional (aaaa-mm-dd)
	 * @return string
	 */
	public function getData(){
		return $this->ano.'-'.$this->mes.'-'.$this->dia;
	}
	
	/**
	 * retorna a data e hora no formato internacional (aaaa-mm-dd hh:mm:ss)
	 * @return string
	 */
	public function getDataHora(){
		return $this->ano.'-'.$this->mes.'-'.$this->dia.' '.$this->hora.':'.$this->minuto.':'.$this->segundo;
	}
	
	/**
	 * retorna o horário (hh:mm:ss)
	 * @return string
	 */
	public function getHorario(){
		return $this->hora.':'.$this->minuto.':'.$this->segundo;
	}
	
	/**
	 * retorna a data por extenso (ex.: 08 de março de 2013)
	 * @return string
	 */
	public function getDataExtenso(){
		$meses = array('janeiro', 'fevereiro', 'março', 'abril', 'maio', 'junho', 'julho', 'agosto', 'setembro', 'outubro', 'novembro', 'dezembro');
		return $this->dia.' de '.$meses[$this->mes - 1].' de '.$this->ano;
	}
	
	/**
	 * @return string
	 */
	public function __toString(){
		return $this->getDataHora();
	}
	
	/**
	 * @param string $data
	 * @return Data
	 */
	public static function getInstance($data){
		return new Data($data);
	}
}
?>