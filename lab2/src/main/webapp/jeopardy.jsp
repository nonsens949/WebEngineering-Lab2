<?xml version="1.0" encoding="UTF-8"?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
!!!!		<form action="BigJeopardyServlet" method= >
			<h2 id="navheading" class="accessibility">Navigation</h2>
			<ul>
				<li><a class="orangelink navigationlink" id="logoutlink" title="Klicke hier um dich abzumelden" href="#" accesskey="l">Abmelden</a></li>
			</ul>
		</form>
		</nav>
		
		<!-- Content -->
		<div role="main"> 
         
         <!-- info -->
         <section id="gameinfo" aria-labelledby="gameinfoinfoheading">
            <h2 id="gameinfoinfoheading" class="accessibility">Spielinformationen</h2>
            <section id="firstplayer" class="playerinfo leader" aria-labelledby="firstplayerheading">
               <h3 id="firstplayerheading" class="accessibility">Führender Spieler</h3>
               <img class="avatar" src="img/avatar/black-widow_head.png" alt="Spieler-Avatar Black Widow" />
               <table>
                  <tr>
                     <th class="accessibility">Spielername</th>
                     <td class="playername">Black Widow (Du)</td>
                  </tr>
                  <tr>
                     <th class="accessibility">Spielerpunkte</th>
                     <td class="playerpoints">2000 €</td>
                  </tr>
               </table>
            </section>
            
         </section>
         </div>
     
    </body>
</html>