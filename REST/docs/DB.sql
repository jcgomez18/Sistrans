DROP TABLE ALQUILERES;
DROP TABLE VIDEOS;


CREATE TABLE VIDEOS 
(
  ID NUMBER NOT NULL,
  NAME VARCHAR2(255 BYTE), 
  DURATION NUMBER,
  CONSTRAINT VIDEOS_PK PRIMARY KEY (ID)

);

CREATE TABLE ALQUILERES 
(
  ID NUMBER NOT NULL,
  VIDEO_ID NUMBER,
  USER_NAME VARCHAR2(255 BYTE), 
  CONSTRAINT ALQUILERES_PK PRIMARY KEY (ID),
  CONSTRAINT FK_VIDEO FOREIGN KEY (VIDEO_ID) REFERENCES VIDEOS(ID)
);

Insert into VIDEOS (ID,NAME,DURATION) values ('1','Harry Potter 1','120');
Insert into VIDEOS (ID,NAME,DURATION) values ('2','Harry Potter 2','200');
Insert into VIDEOS (ID,NAME,DURATION) values ('3','Harry Potter 3','140');
Insert into VIDEOS (ID,NAME,DURATION) values ('4','Harry Potter 4','100');
Insert into VIDEOS (ID,NAME,DURATION) values ('5','Harry Potter 5','150');
Insert into VIDEOS (ID,NAME,DURATION) values ('6','Harry Potter 6','130');
Insert into VIDEOS (ID,NAME,DURATION) values ('7','Harry Potter 7.1','200');
Insert into VIDEOS (ID,NAME,DURATION) values ('8','Harry Potter 7.2','160');
Insert into VIDEOS (ID,NAME,DURATION) values ('9','Lord Of The Rings 1','120');
Insert into VIDEOS (ID,NAME,DURATION) values ('10','Lord Of The Rings 2','150');
Insert into VIDEOS (ID,NAME,DURATION) values ('11','Lord Of The Rings 3','100');


Insert into ALQUILERES (ID,VIDEO_ID,USER_NAME) values ('1','1','Jack');
Insert into ALQUILERES (ID,VIDEO_ID,USER_NAME) values ('2','1','Juan');
Insert into ALQUILERES (ID,VIDEO_ID,USER_NAME) values ('3','2','Pedro');
Insert into ALQUILERES (ID,VIDEO_ID,USER_NAME) values ('4','5','Jack');
Insert into ALQUILERES (ID,VIDEO_ID,USER_NAME) values ('5','10','Nick');
Insert into ALQUILERES (ID,VIDEO_ID,USER_NAME) values ('6','11','Jack');
Insert into ALQUILERES (ID,VIDEO_ID,USER_NAME) values ('7','4','Sara');
Insert into ALQUILERES (ID,VIDEO_ID,USER_NAME) values ('8','7','Jack');
Insert into ALQUILERES (ID,VIDEO_ID,USER_NAME) values ('9','7','Jack');


COMMIT;


