<h1> ChatFodaPraCaralho </h1>

<h2>Trabalho acadêmico</h2>

<strong>Descrição do projeto:</strong>
<p>
  Sistema de chat básico que permite conversação entre usuários.<br />
  Por ser um projeto que trabalha com conexões remotas via RMI, é necessário gerar as classes STUBs.<br />
  Para isto, se necessário, via terminal ou prompt de comando, entrar no diretório da pasta "bin" na raiz do projeto e executar os comandos:
    <ul>
      <li>rmic chat.foda.pra.caralho.rmi.ClienteRemotoImpl</li>
      <li>rmic chat.foda.pra.caralho.rmi.ServidorRemotoImpl</li>
    </ul>
</p>

<a href="https://github.com/lordtecnetos/SiteFodaPraCaralho" target="_blank">Site de divulgação do aplicativo.</a>

<p>
  <strong>Autores:</strong>
  <ul>
      <li>Alessandro Beleboni Belini | <a href="mailto:alessandro.belini_science@hotmail.com">alessandro.belini_science@hotmail.com</a></li>
      <li>Luiz Felipe Nazari | <a href="mailto:luiz.nazari.42@gmail.com">luiz.nazari.42@gmail.com</a></li>
    </ul>
  
</p>

<hr />
<h3>Versão 2.0 - 03/12/2014</h3>

-> Projeto final da disciplina de Programação de Aplicativos (T.A.D.S. - Unoesc Xanxerê / 4º Período)
<br />
-> Professor/Instrutor: André Luiz Forchesatto | andre.forchesatto@unoesc.edu.br

<strong>Novas funcionalidades:</strong>
  <ul>
    <li>Conversas com múltiplos usuários;</li>
    <li>Ao fechar uma conversa, ou realizar logout, a conversa não será mais "destruída" no servidor, apenas quando não houvere nenhum usuário utilizando a mesma;</li>
    <li>Auto detecção das conexões do computador na tela de início da aplicação, listando todos os endereços IPv4;</li>
    <li>Criada janela para facilitar a manipulação de usuários, adicionar/remover amigos e convidar usuários para chat;</li>
    <li>Adicionada possibilidade de adicionar usuários presentes na conversa (que ainda não sejam amigos);</li>
    <li>[Servidor] Ao iniciar pela primeira vez, pede configuração do bando de dados (login e senha) para criar a conexão (dados salvos no arquivo log.txt);</li>
    <li>[Servidor] Adicionado relatório de usuários cadastrados (Utiliza JasperReport);</li>
    <li>[Servidor] Ao fechar o servidor, será iniciada uma contagem regressiva de 30 segundos, avisando todos os usuários que possuem alguma conversa em aberto.</li>
  </ul>
  
  <strong>Comportamentos:</strong>
  <ul>
    <li>As janelas agora são construídas utilizando WebLaF - Look and Feel;</li>
    <li>Tela de cadastro refeita. Mais bonita e eficiente!;</li>
    <li>Possibilitada troca de senha da conta;</li>
    <li>Alterado banco de dados. Bando de Dados Orientado a Objetos (db4o) para Banco de Dados Relacional (MySQL);</li>
    <li>Melhorado relacionamento entre entidades do banco;</li>
    <li>Criada entidade para manter a relação de usuários amigos no banco;</li>
    <li>Trocada dependência por nomes (Funções que utilizam conexão com o banco de dados) para dependência por códigos;</li>
    <li>Agora as conversas abertas são compostas por JInternalFrame e não mais por tabs;</li>
    <li>Ajustados problemas com logout e quando é fechada uma conversa por um usuário (ao fechar o JInternalFrame);</li>
  </ul>
  
  <strong>Problemas da versão:</strong>
  <ul>
    <li>WebLaF não funciona perfeitamente em MACs;</li>
    <li>Não utiliza Java 8 por incompatibilidade com o WebLaF;</li>
    <li>Rolagem automática dos componentes JTextArea não estão funcionando corretamente, provavelmente por causa do Swing ou WebLaF;</li>
    <li>Problemas para reconectar usuários que fizeram parte da mesma conversa, quando ocorre falha na conexão, ou algum dos participantes fecha a conversa ou faz logout, por exemplo, e a conversa não for encerrada.</li>
  </ul>

<hr />
<h3>Versão 1.0 - 26/06/2014</h3> <a href="https://github.com/dr-octopus/chatFodaPraCaralho/commit/42e2e3dea82775b21b1c76126264c09abedf5721">Último commit da versão</a>
<br />

-> Projeto final da disciplina de Programação Orientada a Objetos (T.A.D.S. - Unoesc Xanxerê / 3º Período)
<br />
-> Professor/Instrutor: André Luiz Forchesatto | andre.forchesatto@unoesc.edu.br
<br />
-> O mesmo resultou na nota máxima da matéria.

<strong>Funcionalidades:</strong>
  <ul>
    <li>Cadastro;</li>
    <li>Remover conta;</li>
    <li>Login;</li>
    <li>Logout;</li>
    <li>Alterar nickname;</li>
    <li>Adicionar amigos à lista de amigos;</li>
    <li>Remover amigos da lista de amigos;</li>
    <li>Abrir chat com um amigo específico;</li>
    <li>Conversar alegremente com um amigo;</li>
    <li>[Servidor] Registro de login/logout;</li>
    <li>[Servidor] Enviar mensagem para todos os clientes.</li>
  </ul>

<strong>Problemas da versão:</strong>
  <ul>
    <li>Lógicas do banco de dados não otimizados;</li>
    <li>Problemas em passar objetos pelos canais do RMI para fazer comandos no banco de dados;</li>
    <li>Problemas no logout de clientes com chats abertos (quando são o único usuário no chat), por falta de tempo, contornado precariamente com um try-catch generalizado para não derrubar a aplicação (por causa deste bug, foi removido o botão fechar nas tabs de cada chat aberto);</li>
    <li>Problemas de tabs duplicadas (relacionado ao problema acima).</li>
    <li>Não permite que seja adicionado mais de dois usuário em um mesmo chat (o sistema já suporta este recurso, apenas não é aplicado);</li>
    <li>Utilização precária do banco de dados. (db4o)</li>
  </ul>
<hr> 
