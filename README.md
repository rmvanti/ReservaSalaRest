# ReservaSalaRest


Este projeto implementa um CRUD utilizando jax-rs no lado servidor e javascript no lado cliente.
Na pasta backend esta o projeto do Netbeans que implementa os serviços que serão consumidos. 
Na pasta frontend esta o cliente implementado em javascript.

O projeto java foi implementado com netbeans, mysql, tomcat.

1 - Baixe o projeto e descompacte-o em um diretório de sua preferência;
2 - Crie um novo schema no mysql chamado reservasalarest;
3 - Inicie o netbeans;
4 - Selecione a opção abrir projeto;
5 - Selecione o diretório do projeto que devera estar em backend/ReservaSalaRest;
6 - Acesse no netbeans a aba serviços, caso ela não esteja visível selecione na barra de menus a opção ferramentas->serviços;<br>
7 - No item banco de dados crie uma nova conexão com uma base mysql;<br>
7.1 - Selecione o driver do mysql;<br>
7.2 - Informe os dados de conexão, tais como usuário, senha, o nome do schema (foi criado no mysql , no passo 2);<br>
7.3 - Finalize os dados de conexão.<br>
8 - Volte para a aba projetos e expanda os arquivos do projeto;<br>
9 - Expanda a pasta arquivos de configuração e abra o arquivo persistence.xml, abra este arquivo em mod design;<br>
10 - Selecione no combobox chamado 'Conexão JDBC' a conexão que foi criado agora pouco, mantenha as demais configurações;<br>
11 - Volte na aba serviços que acessamos anteriormente, agora selecione a opção servidores;<br>
12 - Expanda as opções para vizualizar os servidores instalados no seu ambiente;<br>
13 - Certifique-se de todos os servidores estarem parados;<br>
14 - Selecione o servidor Apache Tomcat e clique com o botão direito do mouse sobre ele;<br>
15  - Selecione a opção propriedade; <br>
16 - Altere o número da porta do servidor para 8084;<br>
17 - Salve as todas as configurações;<br>
18 - Execute o projeto.<br>
19 - Abra o arquivo index.html que se encontra no diretório frontend/crud_reserva_sala no seu navegador (se póssivel o gogle chrome). <br>

Obs: Se por qualquer motivo a porta 8084 estiver indisponível, selecione uma porta que esteja disponível para o servidor e altere a url do serviço no script app.js (esta no diretório reservaSalaRest/frontend/crud_reserva-sala/js).
Na linha 4 será incontrada a seguinte instrução: var baseURL = 'http://localhost:8084/ReservaSalaREST/rest';
Altere o número 8084 para o número da porta que esteja disponível que você escolheu. 
