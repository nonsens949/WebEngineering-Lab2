<?xml version="1.0" encoding="UTF-8"?>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import = "java.util.*" %>
<%@ page import = "at.ac.tuwien.big.we15.lab2.api.Answer" %>
<jsp:useBean id="simpleQuestion" scope="session" class="at.ac.tuwien.big.we15.lab2.api.impl.SimpleQuestion"/>
<jsp:useBean id="gameStatus" scope="session" class="at.ac.tuwien.big.we15.lab2.api.impl.JeopardyGameStatus"/>
<jsp:useBean id="user" scope="session" class="at.ac.tuwien.big.we15.lab2.api.impl.UserImpl"/>
<jsp:useBean id="opponent" scope="session" class="at.ac.tuwien.big.we15.lab2.api.impl.UserImpl"/>
<!DOCTYPE html>
<html  xmlns="http://www.w3.org/1999/xhtml" lang="de">
<head>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Business Informatics Group Jeopardy! - Fragenbeantwortung</title>
        <link rel="stylesheet" type="text/css" href="style/base.css" />
        <link rel="stylesheet" type="text/css" href="style/screen.css" />
        <script src="js/jquery.js" type="text/javascript"></script>
        <script src="js/framework.js" type="text/javascript"></script>
    </head>
    <body id="questionpage">
      <a class="accessibility" href="#questions">Zur Fragenauswahl springen</a>
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
               <img class="avatar" src="img/avatar/<%= user.getAvatar().getImageHead() %>" alt="<%= user.getAvatar().getName() %>" />
               <table>
                  <tr>
                     <th class="accessibility">Spielername</th>
                     <td class="playername"><%= user.getUsername() %></td>
                  </tr>
                  <tr>
                     <th class="accessibility">Spielerpunkte</th>
                     <td class="playerpoints"><%= gameStatus.getPlayer1Score() %> €</td>
                  </tr>
               </table>
            </section>
            <section id="secondplayer" class="playerinfo" aria-labelledby="secondplayerheading">
               <h3 id="secondplayerheading" class="accessibility">Zweiter Spieler</h3>
               <img class="avatar" src="img/avatar/<%=opponent.getAvatar().getImageHead() %>" alt="Spieler-Avatar <%= opponent.getUsername() %>" />
               <table>
                  <tr>
                     <th class="accessibility">Spielername</th>
                     <td class="playername"><%= opponent.getUsername() %></td>
                  </tr>
                  <tr>
                     <th class="accessibility">Spielerpunkte</th>
                     <td class="playerpoints"><%= gameStatus.getPlayer2Score() %> €</td>
                  </tr>
               </table>
            </section>
            <p id="round">Frage: <%=gameStatus.getRound() %> / 10</p>
         </section>
            
      <!-- Question -->
      <section id="question" aria-labelledby="questionheading">
      
      		<% 	List<Answer> answers = simpleQuestion.getAllAnswers(); 
      			answers.sort(new Comparator<Answer>() {
      				@Override
      				public int compare(Answer answer1, Answer answer2) {
      					if(answer1.getId() == answer2.getId())
      						return 0;
      					else if(answer1.getId() > answer2.getId())
      						return 1;
      					else
      						return -1;
      				}
      			});        
      		%>
      
            <form id="questionform" action="BigJeopardyServlet" method="post">
               <h2 id="questionheading" class="accessibility">Frage</h2>
               <p id="questiontype"><%= simpleQuestion.getCategory().getName()%> für <%= simpleQuestion.getValue() %> €</p>
               <p id="questiontext"><%= simpleQuestion.getText() %></p>
               <ul id="answers">
               	<%for (Answer a : answers) { %>
               		<li><input name="answers" id="answer_<%=a.getId() %>" value=<%=a.getId() %> type="checkbox"/><label class="tile clickable" for="answer_<%=a.getId() %>"><%= a.getText() %></label></li>
               	<% } %>
               </ul>
               <input id="timeleftvalue" type="hidden" value="100"/>
               <input class="greenlink formlink clickable" name="answer_submit" id="next" type="submit" value="antworten" accesskey="s" disabled="disabled"/>
            </form>
         </section>
            
         <section id="timer" aria-labelledby="timerheading">
            <h2 id="timerheading" class="accessibility">Timer</h2>
            <p><span id="timeleftlabel">Verbleibende Zeit:</span> <time id="timeleft">00:30</time></p>
            <meter id="timermeter" min="0" low="20" value="100" max="100"/>
         </section>
      </div>

      <!-- footer -->
      <footer role="contentinfo">© 2015 BIG Jeopardy!</footer>
        
      <script type="text/javascript">
            //<![CDATA[
            
            // initialize time
            $(document).ready(function() {
                var maxtime = 30;
                var hiddenInput = $("#timeleftvalue");
                var meter = $("#timer meter");
                var timeleft = $("#timeleft");
                
                hiddenInput.val(maxtime);
                meter.val(maxtime);
                meter.attr('max', maxtime);
                meter.attr('low', maxtime/100*20);
                timeleft.text(secToMMSS(maxtime));
            });
            
            // update time
            function timeStep() {
                var hiddenInput = $("#timeleftvalue");
                var meter = $("#timer meter");
                var timeleft = $("#timeleft");
                
                var value = $("#timeleftvalue").val();
                if(value > 0) {
                    value = value - 1;   
                }
                
                hiddenInput.val(value);
                meter.val(value);
                timeleft.text(secToMMSS(value));
                
                if(value <= 0) {
                    $('#questionform').submit();
                }
            }
            
            window.setInterval(timeStep, 1000);
            
            //]]>
        </script>
        
        
        <!-- Submit button is set to disabled as default. When at least one answer is chosen the button will be enabled  -->
        <script type="text/javascript">
        
        	$(function() {
        		
					$("form").change(function() {
					
					submitAnswers = $("input[id='next']");
        			
	        		if ($(":checkbox[name='answers']", ("form[id='questionform']")).is(":checked"))
	        		{
	        			submitAnswers.attr("disabled", false);
	        		}
	        		else {
	        			submitAnswers.attr("disabled", true);
	        		};
        		});		

        	});
        
        </script>
    </body>
</html>