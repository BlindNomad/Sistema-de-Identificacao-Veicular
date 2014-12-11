SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `car_core` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `car_core` ;

-- -----------------------------------------------------
-- Table `car_core`.`condutor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `car_core`.`condutor` (
  `id` INT NOT NULL,
  `nome` VARCHAR(45) NOT NULL,
  `numero_carteira` VARCHAR(20) NOT NULL,
  `validade` DATETIME NOT NULL,
  `pontos` INT NOT NULL,
  `liberado` TINYINT NOT NULL,
  `cartao` VARCHAR(45) NOT NULL,
  `data_inf` DATETIME NULL,
  `tempo_vida` INT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `car_core`.`historico`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `car_core`.`historico` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id_condutor` INT NOT NULL,
  `data_hora` DATETIME NOT NULL,
  `tipo_acesso` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_historico_condutor_idx` (`id_condutor` ASC),
  CONSTRAINT `fk_historico_condutor`
    FOREIGN KEY (`id_condutor`)
    REFERENCES `car_core`.`condutor` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
