/* 
 * File:   main.c
 * Author: thiagoj
 *
 * Created on 21 de Outubro de 2014, 16:34
 */

#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <bluetooth/bluetooth.h>
#include <bluetooth/rfcomm.h>

int saveString(char mensagem[]) {


	FILE *f = fopen("tag.tag", "w");
	if (f == NULL) {
		printf("Error opening file!\n");
		exit(1);
	}

	/* print some text */

	fprintf(f, "%s", mensagem);

	fclose(f);


}

/*
 * 
 */
int main(int argc, char** argv) {


	struct sockaddr_rc loc_addr = {0}, rem_addr = {0};
	char buf[1024] = {0};
	int s, client, bytes_read;
	socklen_t opt = sizeof (rem_addr);

	// allocate socket
	if ((s = socket(AF_BLUETOOTH, SOCK_STREAM, BTPROTO_RFCOMM)) == -1) {
		printf("Problemas na criação do socket\n");
		exit(-1);
	}

	// bind socket to port 1 of the first available 
	// local bluetooth adapter
	loc_addr.rc_family = AF_BLUETOOTH;
	loc_addr.rc_bdaddr = *BDADDR_ANY;
	//str2ba( endereco, &loc_addr.rc_bdaddr );
	loc_addr.rc_channel = (uint8_t) 1;
	if (bind(s, (struct sockaddr *) &loc_addr, sizeof (loc_addr)) == -1) {
		printf("Problemas no bind\n");
		exit(-1);
	}
	printf("Preparando para listen..\n");
	// put socket into listening mode
	if (listen(s, 1) == -1)
		printf("Problemas de listen...\n");

	while (1) {
		printf("Preparando para accept..\n");
		// accept one connection
		client = accept(s, (struct sockaddr *) &rem_addr, &opt);

		printf("Preparando para leitura..\n");
		ba2str(&rem_addr.rc_bdaddr, buf);
		fprintf(stderr, "accepted connection from %s\n", buf);
		memset(buf, 0, sizeof (buf));

		// read data from the client
		bytes_read = read(client, buf, sizeof (buf) + 1);
		if (bytes_read > 0) {
			saveString(buf);
			printf("received [%s]\n", buf);
		}

		//envia uma resposta
		strcpy(buf, "Informacao enviada!");
		write(client, buf, sizeof (buf));
		sleep(10);

		// close connection
		close(client);
	}
	close(s);

	return (EXIT_SUCCESS);
}

