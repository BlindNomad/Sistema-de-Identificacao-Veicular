<?php

	class DefaultController extends BasicController {

		public function indexAction() {

			$pessoa = new Pessoa();
			$buscaPessoas = $pessoa->search();
			$pessoas = $buscaPessoas->execute();
			if ($buscaPessoas->getTotalRows() == 0) {
				Session::set("nivel", 3);
				Session::set("aut", true);
				Session::set("id", NULL);
				Session::commit();

				Request::redirect("/inicial");
			}

			if (Request::isPost()) {
				$post = Request::$post;
				if (isset($post->usuario) && isset($post->senha)) {
					$usuario = new Pessoa();
					$usuario->setSenha($post->senha);
					$usuario->setUsuario($post->usuario);
					$buscaUsuario = $usuario->search();
					$vetorUsuario = $buscaUsuario->execute();
					if (count($vetorUsuario) == 1) {
						$usuario = $vetorUsuario[0];

						if ($usuario->getPermissao() >= 2) {
							Session::set("nivel", $usuario->getPermissao());
							Session::set("aut", true);
							Session::set("id", $usuario->getId());
							Session::commit();

							Request::redirect("/inicial");
						}
					}
				}
			}
		}

	}
	