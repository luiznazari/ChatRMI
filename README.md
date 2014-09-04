<h1> ChatFodaPraCaralho </h1>

<h2>Trabalho acadêmico</h2>

<strong>Descrição do projeto:</strong>
Sistema de chat básico que permite conversação entre usuários.

<hr>
<h3>Versão 1.0</h3>

-> Projeto final da disciplina de Programação Orientada a Objetos (T.A.D.S. - Unoesc Xanxerê / 3º Período)
-> O mesmo resultou na nota máxima da matéria.

<strong>Funções</strong>
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
    <li>Problemas no logout de clientes com chats abertos (quando são o único usuário no chat), por falta de tempo, contornado precariamente com um try-catch generalizado para não derrubar a aplicação (por este bug, foi removido o botão fechar nas tabs de cada chat aberto);</li>
    <li>Problemas de tabs duplicadas (relacionado ao problema acima).</li>
    <li>Não permite que seja adicionado mais de dois usuário em um mesmo chat (o sistema já suporta este recurso) apenas não é aplicado;</li>
    <li>Utilização precária do banco de dados.</li>
  </ul>
