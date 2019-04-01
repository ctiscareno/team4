<% boolean isUserLoggedIn = (boolean) request.getAttribute("isUserLoggedIn"); %>

<!DOCTYPE html>
<html>
<head>
  <title>User Page</title>
  <meta charset="UTF-8">
  <link rel="stylesheet" href="/css/user-page.css">
  <script type="text/javascript" src="js/user-page-loader.js"></script>
      <script type="text/javascript">
         buildUI();
      </script>
  <script type="text/javascript" src="js/navigation-loader.js"></script>
        <script type="text/javascript">
           buildUI();
        </script>
</head>

<body>
    <nav>
      <ul id="navigation">
        <li><a href="/">Home</a></li>

    <%
      if (isUserLoggedIn) {
        String username = (String) request.getAttribute("username");
    %>
        <a href="/user-page.html?user=<%= username %>">Your Page</a>
        <a href="/logout">Logout</a>
    <% } else {   %>
       <a href="/login">Login</a>
    <% } %>

      </ul>
    </nav>
  <div class="center">
    <div class="padded rounded panel center">
      <h1 id="page-title">User Page</h1>
    </div>

    <div class="padded rounded panel center">
      <form id="message-form" action="/messages" method="POST" class="hidden">
        <h2>Enter a new message:</h2>
        <br/>
        <textarea name="text" id="message-input" class="rounded"></textarea>
        <br/>
        <input type="submit" value="Submit" class="rounded">
      </form>
    </div>
    <hr/>

    <div id="message-container">Loading...</div>
  </div>
</div>
</body>
</html>
