---- Création de la table BATCH_JOB_INSTANCE pour H2
--CREATE TABLE BATCH_JOB_INSTANCE  (
--	JOB_INSTANCE_ID BIGINT  NOT NULL PRIMARY KEY ,
--	VERSION BIGINT ,
--	JOB_NAME VARCHAR(100) NOT NULL,
--	JOB_KEY VARCHAR(32) NOT NULL,
--	constraint JOB_INST_UN unique (JOB_NAME, JOB_KEY)
--) ENGINE=InnoDB;