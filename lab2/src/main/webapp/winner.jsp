<?xml version="1.0" encoding="UTF-8"?>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import = "at.ac.tuwien.big.we15.lab2.api.User" %>
<jsp:useBean id="gameStatus" scope="session" class="at.ac.tuwien.big.we15.lab2.api.impl.JeopardyGameStatus"/>
<jsp:useBean id="user" scope="session" class="at.ac.tuwien.big.we15.lab2.api.impl.UserImpl"/>
<jsp:useBean id="opponent" scope="session" class="at.ac.tuwien.big.we15.lab2.api.impl.UserImpl"/>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="de" lang="de">
    <head>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Business Informatics Group Jeopardy! - Gewinnanzeige</title>
        <link rel="stylesheet" type="text/css" href="style/base.css" />
        <link rel="stylesheet" type="text/css" href="style/screen.css" />
		  <script src="js/jquery.js" type="text/javascript"></script>
        <script src="js/framework.js" type="text/javascript"></script>
    </head>
    <body id="winner-page">
      <a class="accessibility" href="#winner">Zur Gewinnanzeige springen</a>
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
         <section id="gameinfo" aria-labelledby="winnerinfoheading">
            <h2 id="winnerinfoheading" class="accessibility">Gewinnerinformationen</h2>
            <% boolean p1correct = gameStatus.getPlayer1Answer(); 
            	boolean p2correct = gameStatus.getPlayer2Answer();  %>
            <<p class="user-info <%= p1correct ?  "positive-change" : "negative-change" %>">Du hast <%= p1correct ? "richtig" : "falsch" %> geantwortet: <%= p1correct ? "+" : "-" %><%= gameStatus.getPlayer1Value() %> €</p>
            <p class="user-info <%= p2correct ?  "positive-change" : "negative-change" %>"><%= opponent.getUsername() %> hat <%= p2correct ? "richtig" : "falsch" %> geantwortet: <%= p2correct ? "+" : "-" %><%= gameStatus.getPlayer2Value() %> €</p>
            <section class="playerinfo leader" aria-labelledby="winnerannouncement">
            <% User winner = gameStatus.getPlayer1Score() > gameStatus.getPlayer2Score() ? user : opponent; %>
            <% User loser = gameStatus.getPlayer1Score() <= gameStatus.getPlayer2Score() ? user : opponent; %>
               <h3 id="winnerannouncement">Gewinner: <%= winner.getUsername()%></h3>
               <img class="avatar" src="img/avatar/<%=winner.getAvatar().getImageFull() %>" alt="Spieler-Avatar <%=winner.getUsername() %>" />
               <table>
                  <tr>
                     <th class="accessibility">Spielername</th>
                     <td class="playername"><%=winner.getUsername() %></td>
                  </tr>
                  <tr>
                     <th class="accessibility">Spielerpunkte</th>
                     <td class="playerpoints"><%= Math.max(gameStatus.getPlayer1Score(), gameStatus.getPlayer2Score()) %></td>
                  </tr>
               </table>
            </section>
            <section class="playerinfo" aria-labelledby="loserheading">
               <h3 id="loserheading" class="accessibility">Verlierer: <%= loser.getUsername() %></h3>
               <img class="avatar" src="img/avatar/<%= loser.getAvatar().getImageHead() %>" alt="Spieler-Avatar <%=loser.getUsername() %>" />
               <table>
                  <tr>
                     <th class="accessibility">Spielername</th>
                     <td class="playername"><%= loser.getUsername() %></td>
                  </tr>
                  <tr>
                     <th class="accessibility">Spielerpunkte</th>
                     <td class="playerpoints"><%=Math.min(gameStatus.getPlayer1Score(), gameStatus.getPlayer2Score()) %></td>
                  </tr>
               </table>
            </section>
         </section>
         <section id="newgame" aria-labelledby="newgameheading">
             <h2 id="newgameheading" class="accessibility">Neues Spiel</h2>
         	<form action="BigJeopardyServlet" method="post">
               	<input class="clickable orangelink contentlink" id="new_game" type="submit" name="restart" value="Neues Spiel" />
            </form>
         </section>
      </div>
        <!-- footer -->
        <footer role="contentinfo">© 2015 BIG Jeopardy</footer>  
		<script type="text/javascript">
        //<![CDATA[
           $(document).ready(function(){
         	   if(supportsLocalStorage()){
         		   localStorage["lastGame"] = new Date().getTime();
         	   }
           });
        //]]>
        </script>  
    </body>
</html>
