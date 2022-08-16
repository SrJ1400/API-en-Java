CREATE SCHEMA supreme_tournaments;

CREATE TABLE supreme_tournaments.gestor ( 
	id_gestor                           int  NOT NULL  AUTO_INCREMENT  PRIMARY KEY,
    
	nombre                              varchar(69),
	email                               varchar(124),
	contrasenya                         varchar(124)
 );
 
 
  CREATE TABLE supreme_tournaments.torneos_individuales ( 
	id_torneo_individual               int  NOT NULL  AUTO_INCREMENT  PRIMARY KEY,
	id_gestor                          int,
    
	nombre_torneo_individual           varchar(69),
	foto_url_torneo_individual         varchar(200),
	descripcion_corta                  varchar(128),
    descripcion_completa               varchar(200),
	nivel                              int,
    solicitudes_maximos                int,
	fecha_finalizacion                 date,
	fecha_inicio	                   date,
	menores_edad                       boolean
 );
 
 

/*El estdo sera Acceptada, Rechazada, Solicitada*/
CREATE TABLE supreme_tournaments.solicitudes ( 
	id_solicitudes                     int  NOT NULL  AUTO_INCREMENT  PRIMARY KEY,
	id_torneo_individual     		   int,
    
	nombre_solicitudes                 varchar(69),
	foto_url_solicitudes               varchar(200),
	fecha_nacimiento				   date,
	datos                              varchar(200),
	datos_publicos                     varchar(128),     
	estado 		                       varchar(69)     
 );
 
 

 
 CREATE TABLE supreme_tournaments.combates_individuales ( 
	id_combates_individuales  		   int  NOT NULL  AUTO_INCREMENT  PRIMARY KEY,
    
	id_torneo_individual     		   int,	
	id_solicitud1             		   int,      
	id_solicitud2             		   int,      
	id_solicitud_ganador      		   int      
 );
 
 

/*Tabla plantilla donde se veran TODOS los solicitados que han guardado los gestores seria lo que llamamos al principio participantes continuos*/

CREATE TABLE supreme_tournaments.solicitudes_continuas ( 
	id_solicitudes_continuas           int  NOT NULL  AUTO_INCREMENT  PRIMARY KEY,
    
	nombre_solicitudes_continuas       varchar(69),
	foto_url_solicitudes_continuas     varchar(200),
	fecha_nacimiento				   date,
	datos                              varchar(200),
	datos_publicos                     varchar(128) 
 );
 
  
 /* *********************************** */
/* RELACIONES */
/* *********************************** */


/*Gestor*/
ALTER TABLE supreme_tournaments.torneos_individuales   ADD FOREIGN KEY (id_gestor)     REFERENCES supreme_tournaments.gestor(id_gestor);

ALTER TABLE  supreme_tournaments.combates_individuales ADD FOREIGN KEY (id_torneo_individual) 	    REFERENCES supreme_tournaments.torneos_individuales (id_torneo_individual);

ALTER TABLE  supreme_tournaments.solicitudes           ADD FOREIGN KEY (id_torneo_individual) 		REFERENCES supreme_tournaments.torneos_individuales(id_torneo_individual);



ALTER TABLE  supreme_tournaments.combates_individuales ADD FOREIGN KEY (id_solicitud1)        REFERENCES supreme_tournaments.solicitudes(id_solicitudes);
ALTER TABLE  supreme_tournaments.combates_individuales ADD FOREIGN KEY (id_solicitud2)        REFERENCES supreme_tournaments.solicitudes(id_solicitudes);
ALTER TABLE  supreme_tournaments.combates_individuales ADD FOREIGN KEY (id_solicitud_ganador) REFERENCES supreme_tournaments.solicitudes(id_solicitudes);


 
 /*----------------------------INSERTS-------------------*/
 
 /*Gestor*/
 INSERT INTO `supreme_tournaments`.`gestor` (`nombre`, `email`, `contrasenya`) VALUES ('Antonio', 'antonio.hidalgo@gmail.com', 'bc2ba8d40a1b98d49a4bfd09b2d11f4394976378e267d62d48bdb6ef812ca964');

/*Torneo*/
 INSERT INTO `supreme_tournaments`.`torneos_individuales` (`id_gestor`, `nombre_torneo_individual`, `foto_url_torneo_individual`, `descripcion_corta`, `descripcion_completa`, `nivel`, `solicitudes_maximos`, `fecha_finalizacion`, `fecha_inicio`, `menores_edad`) VALUES ('1', 'CopoWarUmucraftUHC', 'https://static.wikia.nocookie.net/uhc-espana/images/8/88/Logo_de_UHC_Espa%C3%B1a_Cuarta_Temporada..png/revision/latest?cb=20190626220734&path-prefix=es', 'Tras la muerte de Copo se levanto una gran Gerra', 'Tras la muerte de Copo se levanto una gran Gerra, en la que cada uno eligira de que bando estara, con el Shogunato que lucha por la protecion o con Copo que lucha por la libertad',5 ,10 ,'2023-06-12', '2023-05-12', '1');
 
 
 INSERT INTO `supreme_tournaments`.`torneos_individuales` (`id_gestor`, `nombre_torneo_individual`, `foto_url_torneo_individual`, `descripcion_corta`, `descripcion_completa`, `nivel`, `solicitudes_maximos`, `fecha_finalizacion`, `fecha_inicio`, `menores_edad`) VALUES ('1', 'Boxeo2022', 'https://izquierdazo.com/wp-content/uploads/2022/01/reglas-boxeo.jpg', 'Combate anual de boxeo en Madrid', 'El año pasado tubimos problemas para hacer el torneo anual debido a la pandemia mundial que estamos sufriendo',3 ,2 ,'2022-06-01', '2022-05-12', '0');



/*Solicitudes*/
INSERT INTO `supreme_tournaments`.`solicitudes` (`id_torneo_individual`, `nombre_solicitudes`, `foto_url_solicitudes`, `fecha_nacimiento`, `datos`, `datos_publicos`, `estado`) VALUES ('1', 'Desdmon', 'https://static.wikia.nocookie.net/theassassinscreed/images/6/6a/ACB_Desmond_Render.png/revision/latest/top-crop/width/360/height/450?cb=20140206203553&path-prefix=es', '2002-07-19', 'Desdmo conocido tambien como Hector el Bromista es una gran Persona', 'Siempre intentara contraatacar contra el Shogunato', 'Acceptada');
INSERT INTO `supreme_tournaments`.`solicitudes` (`id_torneo_individual`, `nombre_solicitudes`, `foto_url_solicitudes`, `fecha_nacimiento`, `datos`, `datos_publicos`, `estado`) VALUES ('1', 'Marcos', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQPUtBID6c482HKV5bg3qmPXCz_geh9KYvn6A&usqp=CAU', '2002-04-06', 'Marcos conocido tambien como Rached y clank simplemente un grande', 'Muerte a Paskyyyy ', 'Acceptada');
INSERT INTO `supreme_tournaments`.`solicitudes` (`id_torneo_individual`, `nombre_solicitudes`, `foto_url_solicitudes`, `fecha_nacimiento`, `datos`, `datos_publicos`, `estado`) VALUES ('1', 'Copo Nieve', 'https://dintactividadjuego.blob.core.windows.net/imagenes/Copo.jpg', '2002-09-24', 'Copo nieve un guerrero que vuelve de lo nunca visto', 'Volvi para terminar lo que no pude terminar', 'Acceptada');
INSERT INTO `supreme_tournaments`.`solicitudes` (`id_torneo_individual`, `nombre_solicitudes`, `foto_url_solicitudes`, `fecha_nacimiento`, `datos`, `datos_publicos`, `estado`) VALUES ('1', 'Antonio', 'https://images.wikidexcdn.net/mwuploads/esssbwiki/thumb/8/84/latest/20180613002622/Link_SSBU.png/1200px-Link_SSBU.png', '2002-11-07', 'Antonio heredero de los hidalgo nunca se queda atras', 'Nuevo este mundillo pero poderoso', 'Acceptada');


INSERT INTO `supreme_tournaments`.`solicitudes` (`id_torneo_individual`, `nombre_solicitudes`, `foto_url_solicitudes`, `fecha_nacimiento`, `datos`, `datos_publicos`, `estado`) VALUES ('2', 'Tomas', 'http://1.bp.blogspot.com/-RmP5f7Fqeew/T97bLa5OSwI/AAAAAAAARH4/h1TKzgjQfx0/s1600/kick-ass-johnson.jpg', '2000-06-06', 'Tomas Giogis tambien conocido como la muerte', 'Me vengare de todo aquello que se interponga en mi camino ', 'Acceptada');
INSERT INTO `supreme_tournaments`.`solicitudes` (`id_torneo_individual`, `nombre_solicitudes`, `foto_url_solicitudes`, `fecha_nacimiento`, `datos`, `datos_publicos`, `estado`) VALUES ('2', 'Jorge Llopis', 'https://dintactividadjuego.blob.core.windows.net/imagenes/BoxeoJorge1.jpg', '2002-09-24', 'Jorge tambien conocido como Copo Nieve', 'Me interpuse en el camino y aqui estamos', 'Acceptada');
INSERT INTO `supreme_tournaments`.`solicitudes` (`id_torneo_individual`, `nombre_solicitudes`, `foto_url_solicitudes`, `fecha_nacimiento`, `datos`, `datos_publicos`, `estado`) VALUES ('2', 'Jorge Bernabeu', 'https://dintactividadjuego.blob.core.windows.net/imagenes/JorgeBFino.png', '2002-12-09', 'Jorge Bernabeu tambien conocido como TheHamste o como el legendario TheKiller', 'Vamos contra todos', 'Acceptada');
INSERT INTO `supreme_tournaments`.`solicitudes` (`id_torneo_individual`, `nombre_solicitudes`, `foto_url_solicitudes`, `fecha_nacimiento`, `datos`, `datos_publicos`, `estado`) VALUES ('2', 'Mister Jagger', 'https://bolavip.com/__export/1622060036144/sites/bolavip/img/2021/05/26/jagger-vs-viruz-boxeo_crop1622060034592.jpg_242310155.jpg', '2002-12-09', 'Mister Jagger conocido como el ganador supremo', 'No pienso perder nunca', 'Acceptada');


/*Combate*/ 
INSERT INTO `supreme_tournaments`.`combates_individuales` (`id_torneo_individual`, `id_solicitud1`, `id_solicitud2`) VALUES ('1', '1', '2');

INSERT INTO `supreme_tournaments`.`combates_individuales` (`id_torneo_individual`, `id_solicitud1`, `id_solicitud2`) VALUES ('2', '3', '4');


/*Solicitudes continuas*/
INSERT INTO `supreme_tournaments`.`solicitudes_continuas` (`nombre_solicitudes_continuas`, `foto_url_solicitudes_continuas`, `fecha_nacimiento`, `datos`, `datos_publicos`) VALUES ('Copo Nieve', 'https://image.shutterstock.com/image-photo/snowman-lies-dead-bloody-snow-260nw-1311616436.jpg', '2002-09-21', 'Un guerrero de nieve', 'Guerrero caido en la ultima guerra');

