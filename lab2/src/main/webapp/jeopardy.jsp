<?xml version="1.0" encoding="UTF-8"?>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import = "at.ac.tuwien.big.we15.lab2.api.*" %>
<%@ page import = "at.ac.tuwien.big.we15.lab2.api.impl.*" %>
<%@ page import = "java.util.*" %>
<jsp:useBean id="user" scope="session" class="at.ac.tuwien.big.we15.lab2.api.impl.UserImpl"/>
<jsp:useBean id="gameState" scope="session" class="at.ac.tuwien.big.we15.lab2.api.impl.GameStateImpl"/>
<!DOCTYPE html>
<html  xmlns="http://www.w3.org/1999/xhtml" xml:lang="de" lang="de">
<head>
	<meta http-equiv="Content-Type" content="text/html;" />
	<meta charset="utf-8"/>
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>Business Informatics Group Jeopardy! - Fragenauswahl</title>
	<link rel="stylesheet" type="text/css" href="style/base.css" />
	<link rel="stylesheet" type="text/css" href="style/screen.css" />
	<script src="js/jquery.js" type="text/javascript"></script>
	<script src="js/framework.js" type="text/javascript"></script>
</head>
 <body id="selection-page">
     <a class="accessibility" href="#question-selection">Zur Fragenauswahl springen</a>
     <!-- Header -->
     <header role="banner" aria-labelledby="bannerheading">
         <h1 id="bannerheading">
            <span class="accessibility">Business Informatics Group </span><span class="gametitle">Jeopardy!</span>
         </h1>
      </header>
      
       <!-- Navigation -->
		<nav role="navigation" aria-labelledby="navheading">
			<h2 id="navheading" class="accessibility">Navigation</h2>
			<form id="userlogout" action="BigJeopardyServlet" method="post">
				<input name="logout" class="orangelink navigationlink" id="logoutlink" title="Klicke hier um dich abzumelden" value="Abmelden" type="submit" accesskey="l"/>
			</form>
		</nav>
         
      <!-- Content -->
      <div role="main"> 
         <!-- info -->
         <section id="gameinfo" aria-labelledby="gameinfoinfoheading">
            <h2 id="gameinfoinfoheading" class="accessibility">Spielinformationen</h2>
            <section id="firstplayer" class="playerinfo leader" aria-labelledby="firstplayerheading">
               <h3 id="firstplayerheading" class="accessibility">Führender Spieler</h3>
               <img class="avatar" src="img/avatar/<%=user.getAvatar().getImageHead() %>" alt="Spieler-Avatar <%= user.getAvatar().getName() %>" />
               <table>
                  <tr>
                     <th class="accessibility">Spielername</th>
                     <td class="playername"><%= user.getUsername() %></td>
                  </tr>
                  <tr>
                     <th class="accessibility">Spielerpunkte</th>
                     <td class="playerpoints"><%=gameState.getScorePlayer1() %></td>
                  </tr>
               </table>
            </section>
            <section id="secondplayer" class="playerinfo" aria-labelledby="secondplayerheading">
               <h3 id="secondplayerheading" class="accessibility">Zweiter Spieler</h3>
               <img class="avatar" src="img/avatar/deadpool_head.png" alt="Spieler-Avatar Deadpool" />
               <table>
                  <tr>
                     <th class="accessibility">Spielername</th>
                     <td class="playername">Deadpool</td>
                  </tr>
                  <tr>
                     <th class="accessibility">Spielerpunkte</th>
                     <td class="playerpoints">400 €</td>
                  </tr>
               </table>
            </section>
            <p id="round">Fragen: <%=gameState.getRoundCounter() %> / 10</p>
         </section>

         <!-- Question -->
         <% //TODO schon gewaehlte fragen nicht mehr waehlbar machen %>
         <% ServletContext servletContext = getServletContext();
         ServletJeopardyFactory factory = new ServletJeopardyFactory(servletContext);
         QuestionDataProvider provider = factory.createQuestionDataProvider();
         List<Category> categories = provider.getCategoryData(); %>
         <section id="question-selection" aria-labelledby="questionheading">
            <h2 id="questionheading" class="black accessibility">Jeopardy</h2>
            <p class="user-info positive-change"><%= gameState.getLastPositiveChange() %></p>
            <p class="user-info negative-change"><%= gameState.getLastNegativeChange() %></p>
            <p class="user-info"><%= gameState.getLastNeutralChange() %></p>
            <form id="questionform" action="BigJeopardyServlet" method="post">
            <% int questionCount = 0; %>
               <fieldset>
               <legend class="accessibility">Fragenauswahl</legend>
                <%for (Category cat : categories){ %>
      			<section class="questioncategory" aria-labelledby="<%= cat.getName() %>">
      			<h3 id="<%=cat.getName().replaceAll("\\s","").toLowerCase() %>heading" class="tile category-title"><span class="accessibility">Kategorie: </span><%=cat.getName() %></h3>
      			<ol class="category_questions">
      				<% 
      				List<Question> question = cat.getQuestions();
      					for(Question q : question){ %>
      						<li><input name="question_selection" id="question_<%= ++questionCount %>" value="<%=questionCount %>" type="radio"/><label class="tile clickable" for="question_<%=questionCount %>"><%=q.getValue() %></label></li>
      				<%	} %>
      			</ol>
      			</section>
      			<%} %>
               </fieldset>               
               <input class="greenlink formlink clickable" name="question_submit" id="next" type="submit" value="wählen" accesskey="s" />
            </form>
         </section>
         
         <section id="lastgame" aria-labelledby="lastgameheading">
            <h2 id="lastgameheading" class="accessibility">Letztes Spielinfo</h2>
            <p>Letztes Spiel: Nie</p>
         </section>
		</div>
		
      <!-- footer -->
      <footer role="contentinfo">© 2015 BIG Jeopardy!</footer>
	  
	  <script type="text/javascript">
            //<![CDATA[
            
            // initialize time
            $(document).ready(function() {
                // set last game
                if(supportsLocalStorage()) {
	                var lastGameMillis = parseInt(localStorage['lastGame'])
	                if(!isNaN(parseInt(localStorage['lastGame']))){
	                    var lastGame = new Date(lastGameMillis);
	                	$("#lastgame p").replaceWith('<p>Letztes Spiel: <time datetime="'
	                			+ lastGame.toUTCString()
	                			+ '">'
	                			+ lastGame.toLocaleString()
	                			+ '</time></p>')
	                }
            	}
            });            
            //]]>
        </script>
     
    </body>
</html>