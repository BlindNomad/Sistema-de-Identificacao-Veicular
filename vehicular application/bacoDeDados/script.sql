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
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
