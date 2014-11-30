SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `controleAutomotivoBase` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `controleAutomotivoBase` ;

-- -----------------------------------------------------
-- Table `controleAutomotivoBase`.`pessoa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `controleAutomotivoBase`.`pessoa` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id_pessoa_cima` INT NULL,
  `nome` VARCHAR(45) NOT NULL,
  `cpf` CHAR(11) NOT NULL,
  `telefone` VARCHAR(15) NULL,
  `endereco` VARCHAR(45) NULL,
  `cidade` VARCHAR(45) NULL,
  `estado` CHAR(2) NULL,
  `usuario` VARCHAR(45) NOT NULL,
  `senha` VARCHAR(45) NOT NULL,
  `permissao` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `usuario_UNIQUE` (`usuario` ASC),
  UNIQUE INDEX `cpf_UNIQUE` (`cpf` ASC),
  INDEX `fk_pessoa_pessoa1_idx` (`id_pessoa_cima` ASC),
  CONSTRAINT `fk_pessoa_pessoa1`
    FOREIGN KEY (`id_pessoa_cima`)
    REFERENCES `controleAutomotivoBase`.`pessoa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `controleAutomotivoBase`.`classe`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `controleAutomotivoBase`.`classe` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `classe` CHAR(1) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `controleAutomotivoBase`.`veiculo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `controleAutomotivoBase`.`veiculo` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id_classe` INT NOT NULL,
  `placa` VARCHAR(8) NOT NULL,
  `cor` VARCHAR(45) NULL,
  `renavam` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_veiculo_classe1_idx` (`id_classe` ASC),
  UNIQUE INDEX `placa_UNIQUE` (`placa` ASC),
  CONSTRAINT `fk_veiculo_classe1`
    FOREIGN KEY (`id_classe`)
    REFERENCES `controleAutomotivoBase`.`classe` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `controleAutomotivoBase`.`cartao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `controleAutomotivoBase`.`cartao` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `serial` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `serial_UNIQUE` (`serial` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `controleAutomotivoBase`.`condutor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `controleAutomotivoBase`.`condutor` (
  `id` INT NOT NULL,
  `id_cartao` INT NULL,
  `numero_carteira` VARCHAR(10) NOT NULL,
  `validade` DATETIME NOT NULL,
  `liberado` TINYINT NOT NULL,
  `validade_informacao` INT NOT NULL,
  `pontos_carteira` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX `fk_condutor_pessoa1_idx` (`id` ASC),
  INDEX `fk_condutor_cartoes1_idx` (`id_cartao` ASC),
  UNIQUE INDEX `numero_carteira_UNIQUE` (`numero_carteira` ASC),
  CONSTRAINT `fk_condutor_pessoa1`
    FOREIGN KEY (`id`)
    REFERENCES `controleAutomotivoBase`.`pessoa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_condutor_cartoes1`
    FOREIGN KEY (`id_cartao`)
    REFERENCES `controleAutomotivoBase`.`cartao` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `controleAutomotivoBase`.`historico`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `controleAutomotivoBase`.`historico` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id_veiculo` INT NULL,
  `id_pessoa` INT NULL,
  `data_hora` DATETIME NOT NULL,
  `tipo_acesso` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_historico_veiculo1_idx` (`id_veiculo` ASC),
  INDEX `fk_historico_pessoa1_idx` (`id_pessoa` ASC),
  CONSTRAINT `fk_historico_veiculo1`
    FOREIGN KEY (`id_veiculo`)
    REFERENCES `controleAutomotivoBase`.`veiculo` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_historico_pessoa1`
    FOREIGN KEY (`id_pessoa`)
    REFERENCES `controleAutomotivoBase`.`pessoa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `controleAutomotivoBase`.`veiculo_has_condutor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `controleAutomotivoBase`.`veiculo_has_condutor` (
  `veiculo_id` INT NOT NULL,
  `condutor_id` INT NOT NULL,
  PRIMARY KEY (`veiculo_id`, `condutor_id`),
  INDEX `fk_veiculo_has_condutor_condutor1_idx` (`condutor_id` ASC),
  INDEX `fk_veiculo_has_condutor_veiculo1_idx` (`veiculo_id` ASC),
  CONSTRAINT `fk_veiculo_has_condutor_veiculo1`
    FOREIGN KEY (`veiculo_id`)
    REFERENCES `controleAutomotivoBase`.`veiculo` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_veiculo_has_condutor_condutor1`
    FOREIGN KEY (`condutor_id`)
    REFERENCES `controleAutomotivoBase`.`condutor` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `controleAutomotivoBase`.`classe_has_condutor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `controleAutomotivoBase`.`classe_has_condutor` (
  `classe_id` INT NOT NULL,
  `condutor_id` INT NOT NULL,
  PRIMARY KEY (`classe_id`, `condutor_id`),
  INDEX `fk_classe_has_condutor_condutor1_idx` (`condutor_id` ASC),
  INDEX `fk_classe_has_condutor_classe1_idx` (`classe_id` ASC),
  CONSTRAINT `fk_classe_has_condutor_classe1`
    FOREIGN KEY (`classe_id`)
    REFERENCES `controleAutomotivoBase`.`classe` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_classe_has_condutor_condutor1`
    FOREIGN KEY (`condutor_id`)
    REFERENCES `controleAutomotivoBase`.`condutor` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
