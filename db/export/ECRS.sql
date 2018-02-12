--------------------------------------------------------
--  File created - Tuesday-February-23-2016   
--------------------------------------------------------
DROP TABLE "ECRS"."ACTVTY_RSN_CD" cascade constraints;
DROP TABLE "ECRS"."ADP_CD" cascade constraints;
DROP TABLE "ECRS"."APRSR" cascade constraints;
DROP TABLE "ECRS"."BORRWR" cascade constraints;
DROP TABLE "ECRS"."BORRWR_TYPE_CD" cascade constraints;
DROP TABLE "ECRS"."CASE_STAT_CD" cascade constraints;
DROP TABLE "ECRS"."CNSTRCTN_CD" cascade constraints;
DROP TABLE "ECRS"."DFCNCY_LTR" cascade constraints;
DROP TABLE "ECRS"."DOC" cascade constraints;
DROP TABLE "ECRS"."DOC_UPLD_RSN_CD" cascade constraints;
DROP TABLE "ECRS"."ETHNCTY_CD" cascade constraints;
DROP TABLE "ECRS"."FHA_LOAN_TYPE_CD" cascade constraints;
DROP TABLE "ECRS"."GNDR_CD" cascade constraints;
DROP TABLE "ECRS"."HOC" cascade constraints;
DROP TABLE "ECRS"."HOC_EMPLYEE_ROLE_CD" cascade constraints;
DROP TABLE "ECRS"."HOC_LOAN_RVW_PRSNL" cascade constraints;
DROP TABLE "ECRS"."HOC_RVW" cascade constraints;
DROP TABLE "ECRS"."HOC_RVWR" cascade constraints;
DROP TABLE "ECRS"."HOC_RVWR_AVLBLTY" cascade constraints;
DROP TABLE "ECRS"."HOC_RVW_TEAM" cascade constraints;
DROP TABLE "ECRS"."HOC_SPRVSR" cascade constraints;
DROP TABLE "ECRS"."HOC_TEAM_LEAD" cascade constraints;
DROP TABLE "ECRS"."HUD_RGN_FLD_OFC_CD" cascade constraints;
DROP TABLE "ECRS"."INSPCTR" cascade constraints;
DROP TABLE "ECRS"."LNDR" cascade constraints;
DROP TABLE "ECRS"."LNDR_RESP_LTR" cascade constraints;
DROP TABLE "ECRS"."LOAN" cascade constraints;
DROP TABLE "ECRS"."LOAN_BORRWR" cascade constraints;
DROP TABLE "ECRS"."LOAN_FNDS_TO_CLS" cascade constraints;
DROP TABLE "ECRS"."LOAN_MORTG" cascade constraints;
DROP TABLE "ECRS"."LOAN_OFCR" cascade constraints;
DROP TABLE "ECRS"."LOAN_ORGNTR" cascade constraints;
DROP TABLE "ECRS"."LOAN_PRPS_CD" cascade constraints;
DROP TABLE "ECRS"."LOAN_RTNG" cascade constraints;
DROP TABLE "ECRS"."LOAN_RVW" cascade constraints;
DROP TABLE "ECRS"."LOAN_RVW_ACTVTY" cascade constraints;
DROP TABLE "ECRS"."LOAN_RVW_PRSNL" cascade constraints;
DROP TABLE "ECRS"."LOAN_RVW_SKL_MTRX" cascade constraints;
DROP TABLE "ECRS"."LRS_DFCNCY" cascade constraints;
DROP TABLE "ECRS"."LRS_DFCNCY_CTGRY_CD" cascade constraints;
DROP TABLE "ECRS"."LRS_DFCNCY_CTGRY_TYPE" cascade constraints;
DROP TABLE "ECRS"."LRS_DFCNCY_LTR" cascade constraints;
DROP TABLE "ECRS"."LRS_DFCNCY_TYPE_CD" cascade constraints;
DROP TABLE "ECRS"."LRS_RVW_LVL_CD" cascade constraints;
DROP TABLE "ECRS"."LRS_RVW_TYPE_CD" cascade constraints;
DROP TABLE "ECRS"."LRS_STAT_CD" cascade constraints;
DROP TABLE "ECRS"."MRTL_STAT_CD" cascade constraints;
DROP TABLE "ECRS"."POST_ENDRSMNT_TECH_RVW" cascade constraints;
DROP TABLE "ECRS"."PRSN" cascade constraints;
DROP TABLE "ECRS"."PRSN_RACE" cascade constraints;
DROP TABLE "ECRS"."RACE_CD" cascade constraints;
DROP TABLE "ECRS"."RQUST_FOR_DOC" cascade constraints;
DROP TABLE "ECRS"."SFH_PRPRTY" cascade constraints;
DROP TABLE "ECRS"."TEST_CASE_RVW" cascade constraints;
DROP TABLE "ECRS"."TRBL_AFL_CD" cascade constraints;
DROP TABLE "ECRS"."UNDRWRTNG_TYPE_CD" cascade constraints;
--------------------------------------------------------
--  DDL for Table ACTVTY_RSN_CD
--------------------------------------------------------

  CREATE TABLE "ECRS"."ACTVTY_RSN_CD" 
   (	"ACTVTY_RSN_CD" CHAR(1 BYTE), 
	"ACTVTY_RSN_CD_DESC" VARCHAR2(30 BYTE)
   ) ;
--------------------------------------------------------
--  DDL for Table ADP_CD
--------------------------------------------------------

  CREATE TABLE "ECRS"."ADP_CD" 
   (	"ADP_CD" CHAR(3 BYTE), 
	"ADP_CD_DESC" VARCHAR2(30 BYTE)
   ) ;
--------------------------------------------------------
--  DDL for Table APRSR
--------------------------------------------------------

  CREATE TABLE "ECRS"."APRSR" 
   (	"STD_APRSL_CRTFD_IND" CHAR(1 BYTE) DEFAULT 'N', 
	"ST_LCNCD_IND" CHAR(1 BYTE) DEFAULT 'N', 
	"APRSR_LAST_RVW_DT" DATE, 
	"APRSR_LAST_ASGND_DT" DATE, 
	"APRSR_HUD_TRMNTD_DT" DATE, 
	"APRSR_FLD_RVW_AUTH_IND" CHAR(1 BYTE) DEFAULT 'N', 
	"APRSR_CASE_LD_QT_RATIO_PCT" NUMBER(6,0), 
	"APRSR_AVLBL_IND" CHAR(1 BYTE) DEFAULT 'N', 
	"APRSR_203K_CRTFD_IND" CHAR(1 BYTE) DEFAULT 'N', 
	"APRSR_ID" NUMBER(*,0)
   ) ;
--------------------------------------------------------
--  DDL for Table BORRWR
--------------------------------------------------------

  CREATE TABLE "ECRS"."BORRWR" 
   (	"BORRWR_ID" NUMBER(*,0), 
	"DPNDNT_CNT" NUMBER(*,0), 
	"SELF_EMPLYD_IND" CHAR(1 BYTE) DEFAULT 'N', 
	"RNTNG_IND" CHAR(1 BYTE) DEFAULT 'N', 
	"FRST_TIME_HOME_BYR_IND" CHAR(1 BYTE), 
	"EXPRN_FICO_NBR" NUMBER(*,0), 
	"EQFX_BCN_CR_NBR" NUMBER(*,0), 
	"TRNSNN_EMPRC_CR_NBR" NUMBER(*,0), 
	"RNTNG_YR_CNT" NUMBER(*,0), 
	"BORRWR_TYPE_CD" VARCHAR2(30 BYTE), 
	"TRBL_AFL_CD" VARCHAR2(4 BYTE)
   ) ;
--------------------------------------------------------
--  DDL for Table BORRWR_TYPE_CD
--------------------------------------------------------

  CREATE TABLE "ECRS"."BORRWR_TYPE_CD" 
   (	"BORRWR_TYPE_CD" VARCHAR2(30 BYTE), 
	"BORRWR_TYPE_CD_DESC" VARCHAR2(30 BYTE)
   ) ;
--------------------------------------------------------
--  DDL for Table CASE_STAT_CD
--------------------------------------------------------

  CREATE TABLE "ECRS"."CASE_STAT_CD" 
   (	"CASE_STAT_CD" VARCHAR2(4 BYTE), 
	"CASE_STAT_CD_DESC" VARCHAR2(20 BYTE)
   ) ;
--------------------------------------------------------
--  DDL for Table CNSTRCTN_CD
--------------------------------------------------------

  CREATE TABLE "ECRS"."CNSTRCTN_CD" 
   (	"CNSTRCTN_CD" CHAR(1 BYTE), 
	"CNSTRCTN_CD_DESC" VARCHAR2(30 BYTE)
   ) ;
--------------------------------------------------------
--  DDL for Table DFCNCY_LTR
--------------------------------------------------------

  CREATE TABLE "ECRS"."DFCNCY_LTR" 
   (	"DFCNCY_LTR_ID" NUMBER(*,0), 
	"LOAN_RVW_ID" NUMBER(*,0)
   ) ;
--------------------------------------------------------
--  DDL for Table DOC
--------------------------------------------------------

  CREATE TABLE "ECRS"."DOC" 
   (	"DOC_ID" NUMBER(*,0), 
	"DOC_UPLD_DT" DATE, 
	"DOC_CREATE_DT" DATE, 
	"MAIL_ROOM_PRSN_ID" NUMBER(*,0), 
	"DOC_CMPLTNS_IND" CHAR(1 BYTE) DEFAULT 'N', 
	"DOC_TYPE_CD" CHAR(1 BYTE), 
	"DOC_UPLD_RSN_CD" CHAR(1 BYTE), 
	"DOC_PAGE_CNT" NUMBER(*,0), 
	"SEND_MSGE_TO_RVW_IND" CHAR(1 BYTE) DEFAULT 'N', 
	"DOC_UPLD_MSGE_TXT" VARCHAR2(255 BYTE), 
	"DOC_FILE_NM_TXT" VARCHAR2(255 BYTE), 
	"DOC_FILE_LINK_TXT" VARCHAR2(255 BYTE), 
	"DOC_ASSMNT_DT" DATE, 
	"LCTN_ID" NUMBER(*,0), 
	"DOC_MGMT_ID" VARCHAR2(250 BYTE)
   ) ;
--------------------------------------------------------
--  DDL for Table DOC_UPLD_RSN_CD
--------------------------------------------------------

  CREATE TABLE "ECRS"."DOC_UPLD_RSN_CD" 
   (	"DOC_UPLD_RSN_CD" CHAR(1 BYTE), 
	"DOC_UPLD_RSN_CD_DESC" VARCHAR2(30 BYTE)
   ) ;
--------------------------------------------------------
--  DDL for Table ETHNCTY_CD
--------------------------------------------------------

  CREATE TABLE "ECRS"."ETHNCTY_CD" 
   (	"ETHNCTY_CD" CHAR(1 BYTE), 
	"ETHNCTY_DESC" VARCHAR2(250 BYTE)
   ) ;
--------------------------------------------------------
--  DDL for Table FHA_LOAN_TYPE_CD
--------------------------------------------------------

  CREATE TABLE "ECRS"."FHA_LOAN_TYPE_CD" 
   (	"FHA_LOAN_TYPE_CD" CHAR(1 BYTE), 
	"FHA_LOAN_TYPE_CD_DESC" VARCHAR2(30 BYTE)
   ) ;
--------------------------------------------------------
--  DDL for Table GNDR_CD
--------------------------------------------------------

  CREATE TABLE "ECRS"."GNDR_CD" 
   (	"GNDR_CD" CHAR(1 BYTE), 
	"GNDR_DESC" VARCHAR2(250 BYTE)
   ) ;
--------------------------------------------------------
--  DDL for Table HOC
--------------------------------------------------------

  CREATE TABLE "ECRS"."HOC" 
   (	"HOC_ID" NUMBER(*,0), 
	"HOC_NAME" VARCHAR2(30 BYTE)
   ) ;
--------------------------------------------------------
--  DDL for Table HOC_EMPLYEE_ROLE_CD
--------------------------------------------------------

  CREATE TABLE "ECRS"."HOC_EMPLYEE_ROLE_CD" 
   (	"HOC_EMPLYEE_ROLE_CD" CHAR(1 BYTE), 
	"HOC_EMPLYEE_ROLE_CD_DESC" VARCHAR2(30 BYTE)
   ) ;
--------------------------------------------------------
--  DDL for Table HOC_LOAN_RVW_PRSNL
--------------------------------------------------------

  CREATE TABLE "ECRS"."HOC_LOAN_RVW_PRSNL" 
   (	"ASGN_DT" DATE, 
	"HOC_EMPLYEE_ROLE_CD" CHAR(1 BYTE), 
	"LOAN_RVW_PRSNL_ID" NUMBER(*,0), 
	"HOC_ID" NUMBER(*,0)
   ) ;
--------------------------------------------------------
--  DDL for Table HOC_RVW
--------------------------------------------------------

  CREATE TABLE "ECRS"."HOC_RVW" 
   (	"LRS_RVW_LVL_CD" CHAR(1 BYTE), 
	"LOAN_RVW_ID" NUMBER(*,0), 
	"LOAN_RVW_PRSNL_ID" NUMBER(*,0), 
	"HOC_ID" NUMBER(*,0), 
	"HOC_RVW_ID" NUMBER(38,0)
   ) ;
--------------------------------------------------------
--  DDL for Table HOC_RVWR
--------------------------------------------------------

  CREATE TABLE "ECRS"."HOC_RVWR" 
   (	"LOAN_RVW_PRSNL_ID" NUMBER(*,0), 
	"HOC_ID" NUMBER(*,0)
   ) ;
--------------------------------------------------------
--  DDL for Table HOC_RVWR_AVLBLTY
--------------------------------------------------------

  CREATE TABLE "ECRS"."HOC_RVWR_AVLBLTY" 
   (	"RVWR_AVLBL_HRS_NBR" NUMBER(*,0), 
	"RVWR_STRT_TEAM_DT" DATE, 
	"AVLBL_WEEK_DT" DATE, 
	"LOAN_RVW_PRSNL_ID" NUMBER(*,0), 
	"HOC_ID" NUMBER(*,0)
   ) ;
--------------------------------------------------------
--  DDL for Table HOC_RVW_TEAM
--------------------------------------------------------

  CREATE TABLE "ECRS"."HOC_RVW_TEAM" 
   (	"RVWR_STRT_TEAM_DT" DATE, 
	"RVWR_TEAM_END_DT" DATE, 
	"LOAN_RVW_PRSNL_ID" NUMBER(*,0), 
	"HOC_ID" NUMBER(*,0)
   ) ;
--------------------------------------------------------
--  DDL for Table HOC_SPRVSR
--------------------------------------------------------

  CREATE TABLE "ECRS"."HOC_SPRVSR" 
   (	"LOAN_RVW_PRSNL_ID" NUMBER(*,0), 
	"HOC_ID" NUMBER(*,0)
   ) ;
--------------------------------------------------------
--  DDL for Table HOC_TEAM_LEAD
--------------------------------------------------------

  CREATE TABLE "ECRS"."HOC_TEAM_LEAD" 
   (	"LOAN_RVW_PRSNL_ID" NUMBER(*,0), 
	"HOC_ID" NUMBER(*,0)
   ) ;
--------------------------------------------------------
--  DDL for Table HUD_RGN_FLD_OFC_CD
--------------------------------------------------------

  CREATE TABLE "ECRS"."HUD_RGN_FLD_OFC_CD" 
   (	"HUD_FLD_OFC_CD" CHAR(2 BYTE), 
	"HUD_RGN_CD" CHAR(2 BYTE), 
	"HUD_FLD_OFC_NM" VARCHAR2(40 BYTE), 
	"HUD_FLD_OFC_ST_CD" CHAR(2 BYTE)
   ) ;
--------------------------------------------------------
--  DDL for Table INSPCTR
--------------------------------------------------------

  CREATE TABLE "ECRS"."INSPCTR" 
   (	"INSPCTR_ID" NUMBER(*,0)
   ) ;
--------------------------------------------------------
--  DDL for Table LNDR
--------------------------------------------------------

  CREATE TABLE "ECRS"."LNDR" 
   (	"SPNSR_LNDR_ID" NUMBER(*,0), 
	"PRE_CLSNG_PRCSNG_IND" CHAR(1 BYTE) DEFAULT 'N', 
	"LMTD_DNL_PRTCPTN_IND" CHAR(1 BYTE) DEFAULT 'N', 
	"HIGH_RISK_IND" CHAR(1 BYTE) DEFAULT 'N', 
	"HECM_PRE_CLSNG_IND" CHAR(1 BYTE) DEFAULT 'N', 
	"HECM_CRTFD_IND" CHAR(1 BYTE) DEFAULT 'N', 
	"HECM_CRTFD_DT" DATE, 
	"DRCT_ENDRSMNT_PRCSNG_IND" CHAR(1 BYTE) DEFAULT 'N', 
	"LNDR_CTGRY_CD" CHAR(1 BYTE), 
	"NMLS_ID" NUMBER(*,0), 
	"FHA_APRVL_DT" DATE, 
	"FY_END_NM" DATE, 
	"GNMA_ID" NUMBER(*,0), 
	"INCRPRTN_DT" DATE, 
	"INCRPRTN_ST_CD" CHAR(2 BYTE), 
	"TAX_IDNTFCTN_NBR" NUMBER(9,0), 
	"RCRTFCTN_ID" NUMBER(*,0), 
	"IMF_ID" NUMBER(*,0), 
	"LNDR_ID" NUMBER(*,0)
   ) ;
--------------------------------------------------------
--  DDL for Table LNDR_RESP_LTR
--------------------------------------------------------

  CREATE TABLE "ECRS"."LNDR_RESP_LTR" 
   (	"LNDR_RESP_LTR_ID" NUMBER(*,0), 
	"DFCNCY_MTGTN_CMPLT_IND" CHAR(1 BYTE)
   ) ;
--------------------------------------------------------
--  DDL for Table LOAN
--------------------------------------------------------

  CREATE TABLE "ECRS"."LOAN" 
   (	"LNDR_ID" NUMBER(*,0), 
	"SFH_PRPRTY_ID" NUMBER(*,0), 
	"HOC_ID" NUMBER(*,0), 
	"FHA_CASE_NBR" NUMBER(*,0), 
	"FHA_LOAN_TYPE_CD" CHAR(1 BYTE), 
	"FHA_LOAN_AMT" NUMBER(19,4), 
	"FHA_LOAN_TERM_NBR" NUMBER(*,0), 
	"INSPCTR_ID" NUMBER(*,0), 
	"APRSR_ID" NUMBER(*,0), 
	"PRJCTD_CLSNG_DT" DATE, 
	"HUD_RGN_CD" CHAR(2 BYTE), 
	"HUD_FLD_OFC_CD" CHAR(2 BYTE), 
	"LOAN_APPLCTN_CRTFCTN_IND" CHAR(1 BYTE) DEFAULT 'N', 
	"FHA_CASE_NBR_ASGNMT_DT" DATE, 
	"APRSL_RCVD_DT" DATE, 
	"LOAN_NOTE_TXT" VARCHAR2(255 BYTE), 
	"BORRWR_ID" NUMBER(*,0), 
	"ADP_CD" CHAR(3 BYTE), 
	"LOAN_OFCR_ID" NUMBER(*,0), 
	"LOAN_ORGNTR_ID" NUMBER(*,0), 
	"LOAN_PRPS_CD" CHAR(1 BYTE), 
	"CASE_STAT_CD" VARCHAR2(4 BYTE), 
	"CASE_STAT_DT" DATE, 
	"UNDRWRTNG_TYPE_CD" CHAR(1 BYTE), 
	"LOAN_INTRST_RATE_PCT" NUMBER(2,0), 
	"MORTG_PYMNT_PI_AMT" NUMBER(19,4), 
	"LNDR_CASE_RFRNC_CASE_NBR" NUMBER(*,0), 
	"LOAN_ID" NUMBER(*,0)
   ) ;
--------------------------------------------------------
--  DDL for Table LOAN_BORRWR
--------------------------------------------------------

  CREATE TABLE "ECRS"."LOAN_BORRWR" 
   (	"BORRWR_ID" NUMBER(*,0), 
	"LOAN_ID" NUMBER(*,0)
   ) ;
--------------------------------------------------------
--  DDL for Table LOAN_FNDS_TO_CLS
--------------------------------------------------------

  CREATE TABLE "ECRS"."LOAN_FNDS_TO_CLS" 
   (	"INVSTMNT_RQRD_AMT" NUMBER(19,4), 
	"AST_AVLBL_TOT_AMT" NUMBER(19,4), 
	"BORRWR_CLSNG_COST_AMT" NUMBER(19,4), 
	"RSRV_MNTH_CNT" NUMBER(*,0), 
	"SLR_CNTRBTN_PCT" NUMBER(*,0), 
	"SLR_CNTRBTN_AMT" NUMBER(19,4), 
	"SALE_CNCSN_AMT" NUMBER(19,4), 
	"LOAN_ID" NUMBER(*,0)
   ) ;
--------------------------------------------------------
--  DDL for Table LOAN_MORTG
--------------------------------------------------------

  CREATE TABLE "ECRS"."LOAN_MORTG" 
   (	"MORTG_WTHT_FNCL_LF_FEE_AMT" NUMBER(19,4), 
	"MORTG_WITH_FNCL_LF_FEE_AMT" NUMBER(19,4), 
	"MORTG_PYMNT_PI_AMT" NUMBER(19,4), 
	"PYMNTS_CURR_IND" CHAR(1 BYTE) DEFAULT 'Y', 
	"LG_FEE_FNCL_IND" CHAR(1 BYTE) DEFAULT 'Y', 
	"REAL_EST_TXS_AMT" NUMBER(19,4), 
	"HZRD_FLD_AMT" NUMBER(19,4), 
	"MORTG_OTHR_AMT" NUMBER(19,4), 
	"WRNTY_AMT" NUMBER(19,4), 
	"LOAN_TO_VAL_RATIO_PCT" NUMBER(2,0), 
	"CMBND_LOAN_TO_VAL_RATIO_PCT" NUMBER(2,0), 
	"PREM_MNTHLY_AMT" NUMBER(19,4), 
	"PREM_UPFRNT_RATE_PCT" NUMBER(2,0), 
	"PREM_ANUL_RATE_PCT" NUMBER(2,0), 
	"SALE_PRICE_AMT" NUMBER(19,4), 
	"APRSD_AMT" NUMBER(19,4), 
	"GRND_RENT_LEAN_FEE_AMT" NUMBER(19,4), 
	"UNPD_PRNCPL_BLNC_AMT" NUMBER(19,4), 
	"CURR_HUSNG_EXPNS_AMT" NUMBER(19,4), 
	"GRS_INCM_AMT" NUMBER(19,4), 
	"RQRMNT_TOT_AMT" NUMBER(19,4), 
	"FXD_PYMNT_TOT_AMT" NUMBER(19,4), 
	"FXD_DEBT_TO_INCM_RATIO_TOT_AMT" NUMBER(19,4), 
	"GRNTEE_FEE_PAID_IN_CASH_AMT" NUMBER(19,4), 
	"LOAN_ID" NUMBER(*,0)
   ) ;
--------------------------------------------------------
--  DDL for Table LOAN_OFCR
--------------------------------------------------------

  CREATE TABLE "ECRS"."LOAN_OFCR" 
   (	"LOAN_OFCR_ID" NUMBER(*,0), 
	"NMLS_ID" NUMBER(*,0), 
	"LNDR_ID" NUMBER(*,0)
   ) ;
--------------------------------------------------------
--  DDL for Table LOAN_ORGNTR
--------------------------------------------------------

  CREATE TABLE "ECRS"."LOAN_ORGNTR" 
   (	"LOAN_ORGNTR_ID" NUMBER(*,0), 
	"LOAN_ORGNTR_NAME" VARCHAR2(40 BYTE), 
	"LOAN_ORGNTR_EMAIL" VARCHAR2(40 BYTE)
   ) ;
--------------------------------------------------------
--  DDL for Table LOAN_PRPS_CD
--------------------------------------------------------

  CREATE TABLE "ECRS"."LOAN_PRPS_CD" 
   (	"LOAN_PRPS_CD" CHAR(1 BYTE), 
	"LOAN_PRPS_CD_DESC" VARCHAR2(30 BYTE)
   ) ;
--------------------------------------------------------
--  DDL for Table LOAN_RTNG
--------------------------------------------------------

  CREATE TABLE "ECRS"."LOAN_RTNG" 
   (	"LOAN_RTNG_VALUE" VARCHAR2(300 BYTE), 
	"LOAN_RTNG_ID" NUMBER(38,0), 
	"LOAN_RTNG_DT" DATE, 
	"LOAN_RTNG_LTR_SENT_DT" DATE, 
	"LOAN_RVW_ID" NUMBER(38,0)
   ) ;
--------------------------------------------------------
--  DDL for Table LOAN_RVW
--------------------------------------------------------

  CREATE TABLE "ECRS"."LOAN_RVW" 
   (	"LRS_RVW_TYPE_CD" CHAR(1 BYTE), 
	"SLCTN_SRC_CD" CHAR(1 BYTE), 
	"LRS_STAT_CD" CHAR(1 BYTE), 
	"LOAN_RVW_ID" NUMBER(*,0), 
	"LOAN_ID" NUMBER(*,0)
   ) ;
--------------------------------------------------------
--  DDL for Table LOAN_RVW_ACTVTY
--------------------------------------------------------

  CREATE TABLE "ECRS"."LOAN_RVW_ACTVTY" 
   (	"ACTVTY_STRT_DT" DATE, 
	"ACTVTY_END_DT" DATE, 
	"LOAN_RVW_ID" NUMBER(*,0), 
	"ACTVTY_RSN_CD" CHAR(1 BYTE), 
	"DOC_ID" NUMBER(*,0), 
	"LOAN_RVW_ACTVTY_ID" NUMBER(38,0), 
	"ACTVTY_ID" VARCHAR2(20 BYTE), 
	"ACTVTY_DUE_DT" DATE, 
	"LOAN_ID" NUMBER(38,0), 
	"ACTVTY_STAT" VARCHAR2(20 BYTE)
   ) ;
--------------------------------------------------------
--  DDL for Table LOAN_RVW_PRSNL
--------------------------------------------------------

  CREATE TABLE "ECRS"."LOAN_RVW_PRSNL" 
   (	"LOAN_RVW_PRSNL_ID" NUMBER(*,0)
   ) ;
--------------------------------------------------------
--  DDL for Table LOAN_RVW_SKL_MTRX
--------------------------------------------------------

  CREATE TABLE "ECRS"."LOAN_RVW_SKL_MTRX" 
   (	"SKL_STRT_DT" DATE, 
	"SKL_END_DT" DATE, 
	"FHA_LOAN_TYPE_CD" CHAR(1 BYTE), 
	"LOAN_RVW_PRSNL_ID" NUMBER(*,0)
   ) ;
--------------------------------------------------------
--  DDL for Table LRS_DFCNCY
--------------------------------------------------------

  CREATE TABLE "ECRS"."LRS_DFCNCY" 
   (	"DFCNCY_CMNT_TXT" VARCHAR2(250 BYTE), 
	"DFCNCY_DT" DATE, 
	"LRS_DFCNCY_CTGRY_CD" CHAR(2 BYTE), 
	"LRS_DFCNCY_TYPE_CD" CHAR(1 BYTE), 
	"LOAN_RVW_ID" NUMBER(*,0)
   ) ;
--------------------------------------------------------
--  DDL for Table LRS_DFCNCY_CTGRY_CD
--------------------------------------------------------

  CREATE TABLE "ECRS"."LRS_DFCNCY_CTGRY_CD" 
   (	"LRS_DFCNCY_CTGRY_CD" CHAR(2 BYTE)
   ) ;
--------------------------------------------------------
--  DDL for Table LRS_DFCNCY_CTGRY_TYPE
--------------------------------------------------------

  CREATE TABLE "ECRS"."LRS_DFCNCY_CTGRY_TYPE" 
   (	"LRS_DFCNCY_CTGRY_CD" CHAR(2 BYTE), 
	"LRS_DFCNCY_TYPE_CD" CHAR(1 BYTE)
   ) ;
--------------------------------------------------------
--  DDL for Table LRS_DFCNCY_LTR
--------------------------------------------------------

  CREATE TABLE "ECRS"."LRS_DFCNCY_LTR" 
   (	"DFCNCY_LTR_ID" NUMBER(*,0), 
	"LNDR_RESP_LTR_ID" NUMBER(*,0), 
	"LOAN_RVW_ID" NUMBER(*,0)
   ) ;
--------------------------------------------------------
--  DDL for Table LRS_DFCNCY_TYPE_CD
--------------------------------------------------------

  CREATE TABLE "ECRS"."LRS_DFCNCY_TYPE_CD" 
   (	"LRS_DFCNCY_TYPE_CD" CHAR(1 BYTE), 
	"LRS_DFCNCY_TYPE_CD_DESC" VARCHAR2(30 BYTE)
   ) ;
--------------------------------------------------------
--  DDL for Table LRS_RVW_LVL_CD
--------------------------------------------------------

  CREATE TABLE "ECRS"."LRS_RVW_LVL_CD" 
   (	"LRS_RVW_LVL_CD" CHAR(1 BYTE), 
	"LRS_RVW_LVL_CD_DESC" VARCHAR2(30 BYTE)
   ) ;
--------------------------------------------------------
--  DDL for Table LRS_RVW_TYPE_CD
--------------------------------------------------------

  CREATE TABLE "ECRS"."LRS_RVW_TYPE_CD" 
   (	"LRS_RVW_TYPE_CD" CHAR(1 BYTE), 
	"LRS_RVW_TYPE_CD_DESC" VARCHAR2(60 BYTE)
   ) ;
--------------------------------------------------------
--  DDL for Table LRS_STAT_CD
--------------------------------------------------------

  CREATE TABLE "ECRS"."LRS_STAT_CD" 
   (	"LRS_STAT_CD" CHAR(1 BYTE), 
	"LRS_STAT_CD_DESC" VARCHAR2(30 BYTE)
   ) ;
--------------------------------------------------------
--  DDL for Table MRTL_STAT_CD
--------------------------------------------------------

  CREATE TABLE "ECRS"."MRTL_STAT_CD" 
   (	"MRTL_STAT_CD" CHAR(1 BYTE), 
	"MRTL_STAT_CD_DESC" VARCHAR2(30 BYTE)
   ) ;
--------------------------------------------------------
--  DDL for Table POST_ENDRSMNT_TECH_RVW
--------------------------------------------------------

  CREATE TABLE "ECRS"."POST_ENDRSMNT_TECH_RVW" 
   (	"LOAN_RVW_ID" NUMBER(*,0)
   ) ;
--------------------------------------------------------
--  DDL for Table PRSN
--------------------------------------------------------

  CREATE TABLE "ECRS"."PRSN" 
   (	"PRSN_DOB_DT" DATE, 
	"PRSN_SSN" CHAR(9 BYTE), 
	"GNDR_CD" CHAR(1 BYTE), 
	"ETHNCTY_CD" CHAR(1 BYTE), 
	"PRSN_DSBLD_IND" CHAR(1 BYTE) DEFAULT 'N', 
	"PRSN_STDNT_IND" CHAR(1 BYTE) DEFAULT 'N', 
	"PRSN_REC_EFCTV_DT" DATE, 
	"HOME_PHNE_NUM" NUMBER(10,0), 
	"WORK_PHNE_NUM" NUMBER(10,0), 
	"ADDR_ID" NUMBER(*,0), 
	"PRSN_ID" NUMBER(*,0), 
	"PRSN_TYPE_CD" CHAR(1 BYTE), 
	"FAX_PHN_NBR" NUMBER(10,0), 
	"FRST_NM" VARCHAR2(50 BYTE), 
	"MDL_NM" VARCHAR2(50 BYTE), 
	"LAST_NM" VARCHAR2(50 BYTE), 
	"NM_SFX_CD" VARCHAR2(10 BYTE), 
	"MRTL_STAT_CD" CHAR(1 BYTE)
   ) ;
--------------------------------------------------------
--  DDL for Table PRSN_RACE
--------------------------------------------------------

  CREATE TABLE "ECRS"."PRSN_RACE" 
   (	"PRSN_ID" NUMBER(*,0), 
	"RACE_CD" CHAR(2 BYTE)
   ) ;
--------------------------------------------------------
--  DDL for Table RACE_CD
--------------------------------------------------------

  CREATE TABLE "ECRS"."RACE_CD" 
   (	"RACE_CD" CHAR(2 BYTE), 
	"RACE_DESC" VARCHAR2(250 BYTE)
   ) ;
--------------------------------------------------------
--  DDL for Table RQUST_FOR_DOC
--------------------------------------------------------

  CREATE TABLE "ECRS"."RQUST_FOR_DOC" 
   (	"RQUST_FOR_DOC_ID" NUMBER(*,0), 
	"LOAN_RVW_ID" NUMBER(*,0)
   ) ;
--------------------------------------------------------
--  DDL for Table SFH_PRPRTY
--------------------------------------------------------

  CREATE TABLE "ECRS"."SFH_PRPRTY" 
   (	"SFH_PRPRTY_ID" NUMBER(*,0), 
	"PRPRTY_ADDR_ID" NUMBER(*,0), 
	"SFH_UNIT_CNT" NUMBER(*,0), 
	"PRPRTY_ADDR_VLDTN_IND" CHAR(1 BYTE) DEFAULT 'N', 
	"CNSTRCTN_CD" CHAR(1 BYTE), 
	"BLT_YR" CHAR(4 BYTE), 
	"SLR_IND" CHAR(1 BYTE) DEFAULT 'N', 
	"CNSTRCTN_TO_PRMNNT_IND" CHAR(1 BYTE) DEFAULT 'N', 
	"MNFCTRNG_HUSNG_IND" CHAR(1 BYTE) DEFAULT 'N', 
	"PUD_CND_IND" CHAR(1 BYTE) DEFAULT 'N', 
	"CNSTRCTN_RHB_ESCRW_IND" CHAR(1 BYTE) DEFAULT 'N'
   ) ;
--------------------------------------------------------
--  DDL for Table TEST_CASE_RVW
--------------------------------------------------------

  CREATE TABLE "ECRS"."TEST_CASE_RVW" 
   (	"TEST_CASE_RSLT_DESC" VARCHAR2(250 BYTE), 
	"TEST_CASE_SQNC_NBR" NUMBER, 
	"RVW_CYCL_STRT_DT" DATE, 
	"LOAN_RVW_ID" NUMBER(*,0)
   ) ;
--------------------------------------------------------
--  DDL for Table TRBL_AFL_CD
--------------------------------------------------------

  CREATE TABLE "ECRS"."TRBL_AFL_CD" 
   (	"TRBL_AFL_CD" VARCHAR2(4 BYTE), 
	"TRBL_AFL_NAME" VARCHAR2(40 BYTE)
   ) ;
--------------------------------------------------------
--  DDL for Table UNDRWRTNG_TYPE_CD
--------------------------------------------------------

  CREATE TABLE "ECRS"."UNDRWRTNG_TYPE_CD" 
   (	"UNDRWRTNG_TYPE_CD" CHAR(1 BYTE), 
	"UNDRWRTNG_TYPE_CD_DESC" VARCHAR2(30 BYTE)
   ) ;
REM INSERTING into ECRS.ACTVTY_RSN_CD
SET DEFINE OFF;
Insert into ECRS.ACTVTY_RSN_CD (ACTVTY_RSN_CD,ACTVTY_RSN_CD_DESC) values ('R','Initial Upload');
Insert into ECRS.ACTVTY_RSN_CD (ACTVTY_RSN_CD,ACTVTY_RSN_CD_DESC) values ('U','File Uploaded');
Insert into ECRS.ACTVTY_RSN_CD (ACTVTY_RSN_CD,ACTVTY_RSN_CD_DESC) values ('D','Request for Addl Docs');
REM INSERTING into ECRS.ADP_CD
SET DEFINE OFF;
Insert into ECRS.ADP_CD (ADP_CD,ADP_CD_DESC) values ('1  ','263');
Insert into ECRS.ADP_CD (ADP_CD,ADP_CD_DESC) values ('2  ','734');
Insert into ECRS.ADP_CD (ADP_CD,ADP_CD_DESC) values ('3  ','703');
Insert into ECRS.ADP_CD (ADP_CD,ADP_CD_DESC) values ('4  ','805');
Insert into ECRS.ADP_CD (ADP_CD,ADP_CD_DESC) values ('5  ','729');
Insert into ECRS.ADP_CD (ADP_CD,ADP_CD_DESC) values ('6  ','796');
Insert into ECRS.ADP_CD (ADP_CD,ADP_CD_DESC) values ('7  ','734');
Insert into ECRS.ADP_CD (ADP_CD,ADP_CD_DESC) values ('8  ','749');
Insert into ECRS.ADP_CD (ADP_CD,ADP_CD_DESC) values ('9  ','797');
Insert into ECRS.ADP_CD (ADP_CD,ADP_CD_DESC) values ('10 ','229');
REM INSERTING into ECRS.APRSR
SET DEFINE OFF;
Insert into ECRS.APRSR (STD_APRSL_CRTFD_IND,ST_LCNCD_IND,APRSR_LAST_RVW_DT,APRSR_LAST_ASGND_DT,APRSR_HUD_TRMNTD_DT,APRSR_FLD_RVW_AUTH_IND,APRSR_CASE_LD_QT_RATIO_PCT,APRSR_AVLBL_IND,APRSR_203K_CRTFD_IND,APRSR_ID) values ('Y','N',null,to_date('01-FEB-16','DD-MON-RR'),to_date('01-FEB-16','DD-MON-RR'),'N',10,'N','N',234);
Insert into ECRS.APRSR (STD_APRSL_CRTFD_IND,ST_LCNCD_IND,APRSR_LAST_RVW_DT,APRSR_LAST_ASGND_DT,APRSR_HUD_TRMNTD_DT,APRSR_FLD_RVW_AUTH_IND,APRSR_CASE_LD_QT_RATIO_PCT,APRSR_AVLBL_IND,APRSR_203K_CRTFD_IND,APRSR_ID) values ('Y','N',null,to_date('01-FEB-16','DD-MON-RR'),to_date('01-FEB-16','DD-MON-RR'),'N',10,'N','N',235);
Insert into ECRS.APRSR (STD_APRSL_CRTFD_IND,ST_LCNCD_IND,APRSR_LAST_RVW_DT,APRSR_LAST_ASGND_DT,APRSR_HUD_TRMNTD_DT,APRSR_FLD_RVW_AUTH_IND,APRSR_CASE_LD_QT_RATIO_PCT,APRSR_AVLBL_IND,APRSR_203K_CRTFD_IND,APRSR_ID) values ('Y','N',null,to_date('01-FEB-16','DD-MON-RR'),to_date('01-FEB-16','DD-MON-RR'),'N',10,'N','N',236);
Insert into ECRS.APRSR (STD_APRSL_CRTFD_IND,ST_LCNCD_IND,APRSR_LAST_RVW_DT,APRSR_LAST_ASGND_DT,APRSR_HUD_TRMNTD_DT,APRSR_FLD_RVW_AUTH_IND,APRSR_CASE_LD_QT_RATIO_PCT,APRSR_AVLBL_IND,APRSR_203K_CRTFD_IND,APRSR_ID) values ('Y','N',null,to_date('01-FEB-16','DD-MON-RR'),to_date('01-FEB-16','DD-MON-RR'),'N',10,'N','N',237);
Insert into ECRS.APRSR (STD_APRSL_CRTFD_IND,ST_LCNCD_IND,APRSR_LAST_RVW_DT,APRSR_LAST_ASGND_DT,APRSR_HUD_TRMNTD_DT,APRSR_FLD_RVW_AUTH_IND,APRSR_CASE_LD_QT_RATIO_PCT,APRSR_AVLBL_IND,APRSR_203K_CRTFD_IND,APRSR_ID) values ('Y','N',null,to_date('01-FEB-16','DD-MON-RR'),to_date('01-FEB-16','DD-MON-RR'),'N',10,'N','N',238);
Insert into ECRS.APRSR (STD_APRSL_CRTFD_IND,ST_LCNCD_IND,APRSR_LAST_RVW_DT,APRSR_LAST_ASGND_DT,APRSR_HUD_TRMNTD_DT,APRSR_FLD_RVW_AUTH_IND,APRSR_CASE_LD_QT_RATIO_PCT,APRSR_AVLBL_IND,APRSR_203K_CRTFD_IND,APRSR_ID) values ('Y','N',null,to_date('01-FEB-16','DD-MON-RR'),to_date('01-FEB-16','DD-MON-RR'),'N',10,'N','N',239);
REM INSERTING into ECRS.BORRWR
SET DEFINE OFF;
Insert into ECRS.BORRWR (BORRWR_ID,DPNDNT_CNT,SELF_EMPLYD_IND,RNTNG_IND,FRST_TIME_HOME_BYR_IND,EXPRN_FICO_NBR,EQFX_BCN_CR_NBR,TRNSNN_EMPRC_CR_NBR,RNTNG_YR_CNT,BORRWR_TYPE_CD,TRBL_AFL_CD) values (4,2,'N','N','Y',600,610,620,1,'Primary','CHRK');
Insert into ECRS.BORRWR (BORRWR_ID,DPNDNT_CNT,SELF_EMPLYD_IND,RNTNG_IND,FRST_TIME_HOME_BYR_IND,EXPRN_FICO_NBR,EQFX_BCN_CR_NBR,TRNSNN_EMPRC_CR_NBR,RNTNG_YR_CNT,BORRWR_TYPE_CD,TRBL_AFL_CD) values (1,2,'N','N','Y',600,610,620,1,'Primary','CHRK');
Insert into ECRS.BORRWR (BORRWR_ID,DPNDNT_CNT,SELF_EMPLYD_IND,RNTNG_IND,FRST_TIME_HOME_BYR_IND,EXPRN_FICO_NBR,EQFX_BCN_CR_NBR,TRNSNN_EMPRC_CR_NBR,RNTNG_YR_CNT,BORRWR_TYPE_CD,TRBL_AFL_CD) values (5,2,'N','N','Y',600,610,620,1,'Primary','CHRK');
Insert into ECRS.BORRWR (BORRWR_ID,DPNDNT_CNT,SELF_EMPLYD_IND,RNTNG_IND,FRST_TIME_HOME_BYR_IND,EXPRN_FICO_NBR,EQFX_BCN_CR_NBR,TRNSNN_EMPRC_CR_NBR,RNTNG_YR_CNT,BORRWR_TYPE_CD,TRBL_AFL_CD) values (6,2,'N','N','Y',600,610,620,1,'Primary','CHRK');
Insert into ECRS.BORRWR (BORRWR_ID,DPNDNT_CNT,SELF_EMPLYD_IND,RNTNG_IND,FRST_TIME_HOME_BYR_IND,EXPRN_FICO_NBR,EQFX_BCN_CR_NBR,TRNSNN_EMPRC_CR_NBR,RNTNG_YR_CNT,BORRWR_TYPE_CD,TRBL_AFL_CD) values (7,2,'N','N','Y',600,610,620,1,'Primary','CHRK');
Insert into ECRS.BORRWR (BORRWR_ID,DPNDNT_CNT,SELF_EMPLYD_IND,RNTNG_IND,FRST_TIME_HOME_BYR_IND,EXPRN_FICO_NBR,EQFX_BCN_CR_NBR,TRNSNN_EMPRC_CR_NBR,RNTNG_YR_CNT,BORRWR_TYPE_CD,TRBL_AFL_CD) values (8,2,'N','N','Y',600,610,620,1,'Primary','CHRK');
Insert into ECRS.BORRWR (BORRWR_ID,DPNDNT_CNT,SELF_EMPLYD_IND,RNTNG_IND,FRST_TIME_HOME_BYR_IND,EXPRN_FICO_NBR,EQFX_BCN_CR_NBR,TRNSNN_EMPRC_CR_NBR,RNTNG_YR_CNT,BORRWR_TYPE_CD,TRBL_AFL_CD) values (2,2,'N','N','Y',600,610,620,1,'Primary','CHRK');
Insert into ECRS.BORRWR (BORRWR_ID,DPNDNT_CNT,SELF_EMPLYD_IND,RNTNG_IND,FRST_TIME_HOME_BYR_IND,EXPRN_FICO_NBR,EQFX_BCN_CR_NBR,TRNSNN_EMPRC_CR_NBR,RNTNG_YR_CNT,BORRWR_TYPE_CD,TRBL_AFL_CD) values (3,2,'N','N','Y',600,610,620,1,'Primary','CHRK');
REM INSERTING into ECRS.BORRWR_TYPE_CD
SET DEFINE OFF;
Insert into ECRS.BORRWR_TYPE_CD (BORRWR_TYPE_CD,BORRWR_TYPE_CD_DESC) values ('1','Primary');
REM INSERTING into ECRS.CASE_STAT_CD
SET DEFINE OFF;
Insert into ECRS.CASE_STAT_CD (CASE_STAT_CD,CASE_STAT_CD_DESC) values ('E','Escalated');
Insert into ECRS.CASE_STAT_CD (CASE_STAT_CD,CASE_STAT_CD_DESC) values ('I','In Progress');
Insert into ECRS.CASE_STAT_CD (CASE_STAT_CD,CASE_STAT_CD_DESC) values ('S','Selected for Review');
Insert into ECRS.CASE_STAT_CD (CASE_STAT_CD,CASE_STAT_CD_DESC) values ('A','Assigned to Reviewer');
Insert into ECRS.CASE_STAT_CD (CASE_STAT_CD,CASE_STAT_CD_DESC) values ('C','Review Complete');
REM INSERTING into ECRS.CNSTRCTN_CD
SET DEFINE OFF;
Insert into ECRS.CNSTRCTN_CD (CNSTRCTN_CD,CNSTRCTN_CD_DESC) values ('1','New Construction');
Insert into ECRS.CNSTRCTN_CD (CNSTRCTN_CD,CNSTRCTN_CD_DESC) values ('2','Rehab');
REM INSERTING into ECRS.DFCNCY_LTR
SET DEFINE OFF;
REM INSERTING into ECRS.DOC
SET DEFINE OFF;
Insert into ECRS.DOC (DOC_ID,DOC_UPLD_DT,DOC_CREATE_DT,MAIL_ROOM_PRSN_ID,DOC_CMPLTNS_IND,DOC_TYPE_CD,DOC_UPLD_RSN_CD,DOC_PAGE_CNT,SEND_MSGE_TO_RVW_IND,DOC_UPLD_MSGE_TXT,DOC_FILE_NM_TXT,DOC_FILE_LINK_TXT,DOC_ASSMNT_DT,LCTN_ID,DOC_MGMT_ID) values (301,to_date('25-JAN-16','DD-MON-RR'),null,null,'N','d','R',null,'Y',null,'Screenshot (1).png',null,null,null,'56a671c8e1ac4270fd26fc81');
Insert into ECRS.DOC (DOC_ID,DOC_UPLD_DT,DOC_CREATE_DT,MAIL_ROOM_PRSN_ID,DOC_CMPLTNS_IND,DOC_TYPE_CD,DOC_UPLD_RSN_CD,DOC_PAGE_CNT,SEND_MSGE_TO_RVW_IND,DOC_UPLD_MSGE_TXT,DOC_FILE_NM_TXT,DOC_FILE_LINK_TXT,DOC_ASSMNT_DT,LCTN_ID,DOC_MGMT_ID) values (543,to_date('11-FEB-16','DD-MON-RR'),null,null,'N','d','R',null,'Y',null,'Penguins.jpg',null,null,null,'56bd0b8153810250e8c1aaa9');
Insert into ECRS.DOC (DOC_ID,DOC_UPLD_DT,DOC_CREATE_DT,MAIL_ROOM_PRSN_ID,DOC_CMPLTNS_IND,DOC_TYPE_CD,DOC_UPLD_RSN_CD,DOC_PAGE_CNT,SEND_MSGE_TO_RVW_IND,DOC_UPLD_MSGE_TXT,DOC_FILE_NM_TXT,DOC_FILE_LINK_TXT,DOC_ASSMNT_DT,LCTN_ID,DOC_MGMT_ID) values (545,to_date('11-FEB-16','DD-MON-RR'),null,null,'N','d','R',null,'Y',null,'blocker.png',null,null,null,'56bd0bac53810250e8c1aaad');
Insert into ECRS.DOC (DOC_ID,DOC_UPLD_DT,DOC_CREATE_DT,MAIL_ROOM_PRSN_ID,DOC_CMPLTNS_IND,DOC_TYPE_CD,DOC_UPLD_RSN_CD,DOC_PAGE_CNT,SEND_MSGE_TO_RVW_IND,DOC_UPLD_MSGE_TXT,DOC_FILE_NM_TXT,DOC_FILE_LINK_TXT,DOC_ASSMNT_DT,LCTN_ID,DOC_MGMT_ID) values (547,to_date('11-FEB-16','DD-MON-RR'),null,null,'N','d','R',null,'Y',null,'Lighthouse.jpg',null,null,null,'56bd0bde53810250e8c1aaaf');
Insert into ECRS.DOC (DOC_ID,DOC_UPLD_DT,DOC_CREATE_DT,MAIL_ROOM_PRSN_ID,DOC_CMPLTNS_IND,DOC_TYPE_CD,DOC_UPLD_RSN_CD,DOC_PAGE_CNT,SEND_MSGE_TO_RVW_IND,DOC_UPLD_MSGE_TXT,DOC_FILE_NM_TXT,DOC_FILE_LINK_TXT,DOC_ASSMNT_DT,LCTN_ID,DOC_MGMT_ID) values (557,to_date('11-FEB-16','DD-MON-RR'),null,null,'N','d','R',null,'Y',null,'Desert.jpg',null,null,null,'56bd0e5b53810250e8c1aac3');
Insert into ECRS.DOC (DOC_ID,DOC_UPLD_DT,DOC_CREATE_DT,MAIL_ROOM_PRSN_ID,DOC_CMPLTNS_IND,DOC_TYPE_CD,DOC_UPLD_RSN_CD,DOC_PAGE_CNT,SEND_MSGE_TO_RVW_IND,DOC_UPLD_MSGE_TXT,DOC_FILE_NM_TXT,DOC_FILE_LINK_TXT,DOC_ASSMNT_DT,LCTN_ID,DOC_MGMT_ID) values (451,to_date('02-FEB-16','DD-MON-RR'),null,null,'N','d','R',null,'Y',null,'LRSDraftPhasedProjecScope.pdf',null,null,null,'56b0ca82e4b0758ccd95a9ea');
Insert into ECRS.DOC (DOC_ID,DOC_UPLD_DT,DOC_CREATE_DT,MAIL_ROOM_PRSN_ID,DOC_CMPLTNS_IND,DOC_TYPE_CD,DOC_UPLD_RSN_CD,DOC_PAGE_CNT,SEND_MSGE_TO_RVW_IND,DOC_UPLD_MSGE_TXT,DOC_FILE_NM_TXT,DOC_FILE_LINK_TXT,DOC_ASSMNT_DT,LCTN_ID,DOC_MGMT_ID) values (453,to_date('02-FEB-16','DD-MON-RR'),null,null,'N','d','R',null,'Y',null,'How to get the best out of your ALM tool.pdf',null,null,null,'56b0cbe1e4b0758ccd95a9ec');
Insert into ECRS.DOC (DOC_ID,DOC_UPLD_DT,DOC_CREATE_DT,MAIL_ROOM_PRSN_ID,DOC_CMPLTNS_IND,DOC_TYPE_CD,DOC_UPLD_RSN_CD,DOC_PAGE_CNT,SEND_MSGE_TO_RVW_IND,DOC_UPLD_MSGE_TXT,DOC_FILE_NM_TXT,DOC_FILE_LINK_TXT,DOC_ASSMNT_DT,LCTN_ID,DOC_MGMT_ID) values (461,to_date('02-FEB-16','DD-MON-RR'),null,null,'N','d','R',null,'Y',null,'Screenshot (1).png',null,null,null,'56b11a8de4b0758ccd95a9f4');
Insert into ECRS.DOC (DOC_ID,DOC_UPLD_DT,DOC_CREATE_DT,MAIL_ROOM_PRSN_ID,DOC_CMPLTNS_IND,DOC_TYPE_CD,DOC_UPLD_RSN_CD,DOC_PAGE_CNT,SEND_MSGE_TO_RVW_IND,DOC_UPLD_MSGE_TXT,DOC_FILE_NM_TXT,DOC_FILE_LINK_TXT,DOC_ASSMNT_DT,LCTN_ID,DOC_MGMT_ID) values (501,to_date('09-FEB-16','DD-MON-RR'),null,null,'N','d','R',null,'Y',null,'Screenshot (1).png',null,null,null,'56ba984b6064f3a617db21c8');
Insert into ECRS.DOC (DOC_ID,DOC_UPLD_DT,DOC_CREATE_DT,MAIL_ROOM_PRSN_ID,DOC_CMPLTNS_IND,DOC_TYPE_CD,DOC_UPLD_RSN_CD,DOC_PAGE_CNT,SEND_MSGE_TO_RVW_IND,DOC_UPLD_MSGE_TXT,DOC_FILE_NM_TXT,DOC_FILE_LINK_TXT,DOC_ASSMNT_DT,LCTN_ID,DOC_MGMT_ID) values (503,to_date('09-FEB-16','DD-MON-RR'),null,null,'N','d','R',null,'Y',null,'Screenshot (1).png',null,null,null,'56ba9adc60647dee73b3ed9b');
Insert into ECRS.DOC (DOC_ID,DOC_UPLD_DT,DOC_CREATE_DT,MAIL_ROOM_PRSN_ID,DOC_CMPLTNS_IND,DOC_TYPE_CD,DOC_UPLD_RSN_CD,DOC_PAGE_CNT,SEND_MSGE_TO_RVW_IND,DOC_UPLD_MSGE_TXT,DOC_FILE_NM_TXT,DOC_FILE_LINK_TXT,DOC_ASSMNT_DT,LCTN_ID,DOC_MGMT_ID) values (507,to_date('09-FEB-16','DD-MON-RR'),null,null,'N','d','R',null,'Y',null,'Screenshot (1).png',null,null,null,'56ba9e0860644a61e958a999');
Insert into ECRS.DOC (DOC_ID,DOC_UPLD_DT,DOC_CREATE_DT,MAIL_ROOM_PRSN_ID,DOC_CMPLTNS_IND,DOC_TYPE_CD,DOC_UPLD_RSN_CD,DOC_PAGE_CNT,SEND_MSGE_TO_RVW_IND,DOC_UPLD_MSGE_TXT,DOC_FILE_NM_TXT,DOC_FILE_LINK_TXT,DOC_ASSMNT_DT,LCTN_ID,DOC_MGMT_ID) values (509,to_date('09-FEB-16','DD-MON-RR'),null,null,'N','d','R',null,'Y',null,'Screenshot (1).png',null,null,null,'56baa3ef6064e6d3729a86e1');
Insert into ECRS.DOC (DOC_ID,DOC_UPLD_DT,DOC_CREATE_DT,MAIL_ROOM_PRSN_ID,DOC_CMPLTNS_IND,DOC_TYPE_CD,DOC_UPLD_RSN_CD,DOC_PAGE_CNT,SEND_MSGE_TO_RVW_IND,DOC_UPLD_MSGE_TXT,DOC_FILE_NM_TXT,DOC_FILE_LINK_TXT,DOC_ASSMNT_DT,LCTN_ID,DOC_MGMT_ID) values (513,to_date('10-FEB-16','DD-MON-RR'),null,null,'N','d','R',null,'Y',null,'Desert.jpg',null,null,null,'56bb757c007909447ab0b9ce');
Insert into ECRS.DOC (DOC_ID,DOC_UPLD_DT,DOC_CREATE_DT,MAIL_ROOM_PRSN_ID,DOC_CMPLTNS_IND,DOC_TYPE_CD,DOC_UPLD_RSN_CD,DOC_PAGE_CNT,SEND_MSGE_TO_RVW_IND,DOC_UPLD_MSGE_TXT,DOC_FILE_NM_TXT,DOC_FILE_LINK_TXT,DOC_ASSMNT_DT,LCTN_ID,DOC_MGMT_ID) values (516,to_date('10-FEB-16','DD-MON-RR'),null,null,'N','d','R',null,'Y',null,'Screenshot (1).png',null,null,null,'56bba603e733437ce6d10d12');
Insert into ECRS.DOC (DOC_ID,DOC_UPLD_DT,DOC_CREATE_DT,MAIL_ROOM_PRSN_ID,DOC_CMPLTNS_IND,DOC_TYPE_CD,DOC_UPLD_RSN_CD,DOC_PAGE_CNT,SEND_MSGE_TO_RVW_IND,DOC_UPLD_MSGE_TXT,DOC_FILE_NM_TXT,DOC_FILE_LINK_TXT,DOC_ASSMNT_DT,LCTN_ID,DOC_MGMT_ID) values (524,to_date('11-FEB-16','DD-MON-RR'),null,null,'N','d','R',null,'Y',null,'Screenshot (1).png',null,null,null,'56bbe913e4b0d80f48a6725e');
Insert into ECRS.DOC (DOC_ID,DOC_UPLD_DT,DOC_CREATE_DT,MAIL_ROOM_PRSN_ID,DOC_CMPLTNS_IND,DOC_TYPE_CD,DOC_UPLD_RSN_CD,DOC_PAGE_CNT,SEND_MSGE_TO_RVW_IND,DOC_UPLD_MSGE_TXT,DOC_FILE_NM_TXT,DOC_FILE_LINK_TXT,DOC_ASSMNT_DT,LCTN_ID,DOC_MGMT_ID) values (526,to_date('11-FEB-16','DD-MON-RR'),null,null,'N','d','R',null,'Y',null,'Screenshot (1).png',null,null,null,'56bbf96ee4b0d80f48a67260');
Insert into ECRS.DOC (DOC_ID,DOC_UPLD_DT,DOC_CREATE_DT,MAIL_ROOM_PRSN_ID,DOC_CMPLTNS_IND,DOC_TYPE_CD,DOC_UPLD_RSN_CD,DOC_PAGE_CNT,SEND_MSGE_TO_RVW_IND,DOC_UPLD_MSGE_TXT,DOC_FILE_NM_TXT,DOC_FILE_LINK_TXT,DOC_ASSMNT_DT,LCTN_ID,DOC_MGMT_ID) values (561,to_date('11-FEB-16','DD-MON-RR'),null,null,'N','d','R',null,'Y',null,'Lighthouse.jpg',null,null,null,'56bd0edf53810250e8c1aacc');
Insert into ECRS.DOC (DOC_ID,DOC_UPLD_DT,DOC_CREATE_DT,MAIL_ROOM_PRSN_ID,DOC_CMPLTNS_IND,DOC_TYPE_CD,DOC_UPLD_RSN_CD,DOC_PAGE_CNT,SEND_MSGE_TO_RVW_IND,DOC_UPLD_MSGE_TXT,DOC_FILE_NM_TXT,DOC_FILE_LINK_TXT,DOC_ASSMNT_DT,LCTN_ID,DOC_MGMT_ID) values (197,to_date('19-JAN-16','DD-MON-RR'),null,null,'N','d','R',null,'Y',null,'level-2-dfd-diagram-example-5416.png',null,null,null,'569ea0343189d2502f4db12f');
Insert into ECRS.DOC (DOC_ID,DOC_UPLD_DT,DOC_CREATE_DT,MAIL_ROOM_PRSN_ID,DOC_CMPLTNS_IND,DOC_TYPE_CD,DOC_UPLD_RSN_CD,DOC_PAGE_CNT,SEND_MSGE_TO_RVW_IND,DOC_UPLD_MSGE_TXT,DOC_FILE_NM_TXT,DOC_FILE_LINK_TXT,DOC_ASSMNT_DT,LCTN_ID,DOC_MGMT_ID) values (261,to_date('21-JAN-16','DD-MON-RR'),null,null,'N','d','R',null,'Y',null,'HUD  CaReS additional Items  for SLAP .docx',null,null,null,'56a10262e4b0d586cbed1023');
Insert into ECRS.DOC (DOC_ID,DOC_UPLD_DT,DOC_CREATE_DT,MAIL_ROOM_PRSN_ID,DOC_CMPLTNS_IND,DOC_TYPE_CD,DOC_UPLD_RSN_CD,DOC_PAGE_CNT,SEND_MSGE_TO_RVW_IND,DOC_UPLD_MSGE_TXT,DOC_FILE_NM_TXT,DOC_FILE_LINK_TXT,DOC_ASSMNT_DT,LCTN_ID,DOC_MGMT_ID) values (263,to_date('21-JAN-16','DD-MON-RR'),null,null,'N','d','R',null,'Y',null,'Loans Assigned to Each HOC .pdf',null,null,null,'56a10272e4b0d586cbed1025');
Insert into ECRS.DOC (DOC_ID,DOC_UPLD_DT,DOC_CREATE_DT,MAIL_ROOM_PRSN_ID,DOC_CMPLTNS_IND,DOC_TYPE_CD,DOC_UPLD_RSN_CD,DOC_PAGE_CNT,SEND_MSGE_TO_RVW_IND,DOC_UPLD_MSGE_TXT,DOC_FILE_NM_TXT,DOC_FILE_LINK_TXT,DOC_ASSMNT_DT,LCTN_ID,DOC_MGMT_ID) values (265,to_date('21-JAN-16','DD-MON-RR'),null,null,'N','d','R',null,'Y',null,'Loans Assigned to Each HOC .pdf',null,null,null,'56a1027ae4b0d586cbed1027');
Insert into ECRS.DOC (DOC_ID,DOC_UPLD_DT,DOC_CREATE_DT,MAIL_ROOM_PRSN_ID,DOC_CMPLTNS_IND,DOC_TYPE_CD,DOC_UPLD_RSN_CD,DOC_PAGE_CNT,SEND_MSGE_TO_RVW_IND,DOC_UPLD_MSGE_TXT,DOC_FILE_NM_TXT,DOC_FILE_LINK_TXT,DOC_ASSMNT_DT,LCTN_ID,DOC_MGMT_ID) values (267,to_date('21-JAN-16','DD-MON-RR'),null,null,'N','d','R',null,'Y',null,'LRSDEMO-Menu.pdf',null,null,null,'56a10285e4b0d586cbed1029');
Insert into ECRS.DOC (DOC_ID,DOC_UPLD_DT,DOC_CREATE_DT,MAIL_ROOM_PRSN_ID,DOC_CMPLTNS_IND,DOC_TYPE_CD,DOC_UPLD_RSN_CD,DOC_PAGE_CNT,SEND_MSGE_TO_RVW_IND,DOC_UPLD_MSGE_TXT,DOC_FILE_NM_TXT,DOC_FILE_LINK_TXT,DOC_ASSMNT_DT,LCTN_ID,DOC_MGMT_ID) values (404,to_date('28-JAN-16','DD-MON-RR'),null,null,'N','d','R',null,'Y',null,'Screenshot (1).png',null,null,null,'56aa5f1ae1ac39e4af4acf8d');
Insert into ECRS.DOC (DOC_ID,DOC_UPLD_DT,DOC_CREATE_DT,MAIL_ROOM_PRSN_ID,DOC_CMPLTNS_IND,DOC_TYPE_CD,DOC_UPLD_RSN_CD,DOC_PAGE_CNT,SEND_MSGE_TO_RVW_IND,DOC_UPLD_MSGE_TXT,DOC_FILE_NM_TXT,DOC_FILE_LINK_TXT,DOC_ASSMNT_DT,LCTN_ID,DOC_MGMT_ID) values (435,to_date('29-JAN-16','DD-MON-RR'),null,null,'N','d','R',null,'Y',null,'blocker.png',null,null,null,'56ab977131895a7ff7b94ada');
Insert into ECRS.DOC (DOC_ID,DOC_UPLD_DT,DOC_CREATE_DT,MAIL_ROOM_PRSN_ID,DOC_CMPLTNS_IND,DOC_TYPE_CD,DOC_UPLD_RSN_CD,DOC_PAGE_CNT,SEND_MSGE_TO_RVW_IND,DOC_UPLD_MSGE_TXT,DOC_FILE_NM_TXT,DOC_FILE_LINK_TXT,DOC_ASSMNT_DT,LCTN_ID,DOC_MGMT_ID) values (445,to_date('02-FEB-16','DD-MON-RR'),null,null,'N','d','R',null,'Y',null,'HUD IT Asset Inventory 02282016.xlsx',null,null,null,'56b0c2a8e4b0758ccd95a9e6');
Insert into ECRS.DOC (DOC_ID,DOC_UPLD_DT,DOC_CREATE_DT,MAIL_ROOM_PRSN_ID,DOC_CMPLTNS_IND,DOC_TYPE_CD,DOC_UPLD_RSN_CD,DOC_PAGE_CNT,SEND_MSGE_TO_RVW_IND,DOC_UPLD_MSGE_TXT,DOC_FILE_NM_TXT,DOC_FILE_LINK_TXT,DOC_ASSMNT_DT,LCTN_ID,DOC_MGMT_ID) values (447,to_date('02-FEB-16','DD-MON-RR'),null,null,'N','d','R',null,'Y',null,'LRSDEMO-LD-9 Case review.pdf',null,null,null,'56b0c3f2e4b0758ccd95a9e8');
Insert into ECRS.DOC (DOC_ID,DOC_UPLD_DT,DOC_CREATE_DT,MAIL_ROOM_PRSN_ID,DOC_CMPLTNS_IND,DOC_TYPE_CD,DOC_UPLD_RSN_CD,DOC_PAGE_CNT,SEND_MSGE_TO_RVW_IND,DOC_UPLD_MSGE_TXT,DOC_FILE_NM_TXT,DOC_FILE_LINK_TXT,DOC_ASSMNT_DT,LCTN_ID,DOC_MGMT_ID) values (455,to_date('02-FEB-16','DD-MON-RR'),null,null,'N','d','R',null,'Y',null,'HUD_Mortgage_Insurance_LoS_Diagram_39803.pdf',null,null,null,'56b0da8de4b0758ccd95a9ee');
Insert into ECRS.DOC (DOC_ID,DOC_UPLD_DT,DOC_CREATE_DT,MAIL_ROOM_PRSN_ID,DOC_CMPLTNS_IND,DOC_TYPE_CD,DOC_UPLD_RSN_CD,DOC_PAGE_CNT,SEND_MSGE_TO_RVW_IND,DOC_UPLD_MSGE_TXT,DOC_FILE_NM_TXT,DOC_FILE_LINK_TXT,DOC_ASSMNT_DT,LCTN_ID,DOC_MGMT_ID) values (271,to_date('21-JAN-16','DD-MON-RR'),null,null,'N','d','R',null,'Y',null,'LRSDEMO-LD-9 Case review.pdf',null,null,null,'56a135d7e1ace7200484029a');
Insert into ECRS.DOC (DOC_ID,DOC_UPLD_DT,DOC_CREATE_DT,MAIL_ROOM_PRSN_ID,DOC_CMPLTNS_IND,DOC_TYPE_CD,DOC_UPLD_RSN_CD,DOC_PAGE_CNT,SEND_MSGE_TO_RVW_IND,DOC_UPLD_MSGE_TXT,DOC_FILE_NM_TXT,DOC_FILE_LINK_TXT,DOC_ASSMNT_DT,LCTN_ID,DOC_MGMT_ID) values (443,to_date('02-FEB-16','DD-MON-RR'),null,null,'N','d','R',null,'Y',null,'Federal Agency Preliminary Information Technology Asset Inventory.pdf',null,null,null,'56b0c225e4b0758ccd95a9e3');
Insert into ECRS.DOC (DOC_ID,DOC_UPLD_DT,DOC_CREATE_DT,MAIL_ROOM_PRSN_ID,DOC_CMPLTNS_IND,DOC_TYPE_CD,DOC_UPLD_RSN_CD,DOC_PAGE_CNT,SEND_MSGE_TO_RVW_IND,DOC_UPLD_MSGE_TXT,DOC_FILE_NM_TXT,DOC_FILE_LINK_TXT,DOC_ASSMNT_DT,LCTN_ID,DOC_MGMT_ID) values (458,to_date('02-FEB-16','DD-MON-RR'),null,null,'N','d','R',null,'Y',null,'Mortgage Insurance Mind Map.pdf',null,null,null,'56b0fb27e4b0758ccd95a9f2');
Insert into ECRS.DOC (DOC_ID,DOC_UPLD_DT,DOC_CREATE_DT,MAIL_ROOM_PRSN_ID,DOC_CMPLTNS_IND,DOC_TYPE_CD,DOC_UPLD_RSN_CD,DOC_PAGE_CNT,SEND_MSGE_TO_RVW_IND,DOC_UPLD_MSGE_TXT,DOC_FILE_NM_TXT,DOC_FILE_LINK_TXT,DOC_ASSMNT_DT,LCTN_ID,DOC_MGMT_ID) values (511,to_date('10-FEB-16','DD-MON-RR'),null,null,'N','d','R',null,'Y',null,'Penguins.jpg',null,null,null,'56bb7237007909447ab0b9ca');
Insert into ECRS.DOC (DOC_ID,DOC_UPLD_DT,DOC_CREATE_DT,MAIL_ROOM_PRSN_ID,DOC_CMPLTNS_IND,DOC_TYPE_CD,DOC_UPLD_RSN_CD,DOC_PAGE_CNT,SEND_MSGE_TO_RVW_IND,DOC_UPLD_MSGE_TXT,DOC_FILE_NM_TXT,DOC_FILE_LINK_TXT,DOC_ASSMNT_DT,LCTN_ID,DOC_MGMT_ID) values (518,to_date('10-FEB-16','DD-MON-RR'),null,null,'N','d','R',null,'Y',null,'Screenshot (1).png',null,null,null,'56bbb0ede73336bbc1012b20');
Insert into ECRS.DOC (DOC_ID,DOC_UPLD_DT,DOC_CREATE_DT,MAIL_ROOM_PRSN_ID,DOC_CMPLTNS_IND,DOC_TYPE_CD,DOC_UPLD_RSN_CD,DOC_PAGE_CNT,SEND_MSGE_TO_RVW_IND,DOC_UPLD_MSGE_TXT,DOC_FILE_NM_TXT,DOC_FILE_LINK_TXT,DOC_ASSMNT_DT,LCTN_ID,DOC_MGMT_ID) values (520,to_date('10-FEB-16','DD-MON-RR'),null,null,'N','d','R',null,'Y',null,'Screenshot (1).png',null,null,null,'56bbb1f1e7338ec8bab588da');
Insert into ECRS.DOC (DOC_ID,DOC_UPLD_DT,DOC_CREATE_DT,MAIL_ROOM_PRSN_ID,DOC_CMPLTNS_IND,DOC_TYPE_CD,DOC_UPLD_RSN_CD,DOC_PAGE_CNT,SEND_MSGE_TO_RVW_IND,DOC_UPLD_MSGE_TXT,DOC_FILE_NM_TXT,DOC_FILE_LINK_TXT,DOC_ASSMNT_DT,LCTN_ID,DOC_MGMT_ID) values (531,to_date('11-FEB-16','DD-MON-RR'),null,null,'N','d','R',null,'Y',null,'hud.png',null,null,null,'56bcbe80e4b0d80f48a67264');
Insert into ECRS.DOC (DOC_ID,DOC_UPLD_DT,DOC_CREATE_DT,MAIL_ROOM_PRSN_ID,DOC_CMPLTNS_IND,DOC_TYPE_CD,DOC_UPLD_RSN_CD,DOC_PAGE_CNT,SEND_MSGE_TO_RVW_IND,DOC_UPLD_MSGE_TXT,DOC_FILE_NM_TXT,DOC_FILE_LINK_TXT,DOC_ASSMNT_DT,LCTN_ID,DOC_MGMT_ID) values (533,to_date('11-FEB-16','DD-MON-RR'),null,null,'N','d','R',null,'Y',null,'Screenshot (1).png',null,null,null,'56bcf361e4b0d80f48a67266');
Insert into ECRS.DOC (DOC_ID,DOC_UPLD_DT,DOC_CREATE_DT,MAIL_ROOM_PRSN_ID,DOC_CMPLTNS_IND,DOC_TYPE_CD,DOC_UPLD_RSN_CD,DOC_PAGE_CNT,SEND_MSGE_TO_RVW_IND,DOC_UPLD_MSGE_TXT,DOC_FILE_NM_TXT,DOC_FILE_LINK_TXT,DOC_ASSMNT_DT,LCTN_ID,DOC_MGMT_ID) values (541,to_date('11-FEB-16','DD-MON-RR'),null,null,'N','d','R',null,'Y',null,'Penguins.jpg',null,null,null,'56bd081c53810250e8c1aaa5');
Insert into ECRS.DOC (DOC_ID,DOC_UPLD_DT,DOC_CREATE_DT,MAIL_ROOM_PRSN_ID,DOC_CMPLTNS_IND,DOC_TYPE_CD,DOC_UPLD_RSN_CD,DOC_PAGE_CNT,SEND_MSGE_TO_RVW_IND,DOC_UPLD_MSGE_TXT,DOC_FILE_NM_TXT,DOC_FILE_LINK_TXT,DOC_ASSMNT_DT,LCTN_ID,DOC_MGMT_ID) values (549,to_date('11-FEB-16','DD-MON-RR'),null,null,'N','d','R',null,'Y',null,'Lighthouse.jpg',null,null,null,'56bd0bf253810250e8c1aab3');
Insert into ECRS.DOC (DOC_ID,DOC_UPLD_DT,DOC_CREATE_DT,MAIL_ROOM_PRSN_ID,DOC_CMPLTNS_IND,DOC_TYPE_CD,DOC_UPLD_RSN_CD,DOC_PAGE_CNT,SEND_MSGE_TO_RVW_IND,DOC_UPLD_MSGE_TXT,DOC_FILE_NM_TXT,DOC_FILE_LINK_TXT,DOC_ASSMNT_DT,LCTN_ID,DOC_MGMT_ID) values (551,to_date('11-FEB-16','DD-MON-RR'),null,null,'N','d','R',null,'Y',null,'Lighthouse.jpg',null,null,null,'56bd0c1453810250e8c1aab7');
Insert into ECRS.DOC (DOC_ID,DOC_UPLD_DT,DOC_CREATE_DT,MAIL_ROOM_PRSN_ID,DOC_CMPLTNS_IND,DOC_TYPE_CD,DOC_UPLD_RSN_CD,DOC_PAGE_CNT,SEND_MSGE_TO_RVW_IND,DOC_UPLD_MSGE_TXT,DOC_FILE_NM_TXT,DOC_FILE_LINK_TXT,DOC_ASSMNT_DT,LCTN_ID,DOC_MGMT_ID) values (193,to_date('19-JAN-16','DD-MON-RR'),null,null,'N','d','R',null,'Y',null,'Screenshot (1).png',null,null,null,'569e99cbe1ace06b9d3f4e19');
Insert into ECRS.DOC (DOC_ID,DOC_UPLD_DT,DOC_CREATE_DT,MAIL_ROOM_PRSN_ID,DOC_CMPLTNS_IND,DOC_TYPE_CD,DOC_UPLD_RSN_CD,DOC_PAGE_CNT,SEND_MSGE_TO_RVW_IND,DOC_UPLD_MSGE_TXT,DOC_FILE_NM_TXT,DOC_FILE_LINK_TXT,DOC_ASSMNT_DT,LCTN_ID,DOC_MGMT_ID) values (199,to_date('19-JAN-16','DD-MON-RR'),null,null,'N','d','R',null,'Y',null,'Desert.jpg',null,null,null,'569ea0613189d2502f4db131');
Insert into ECRS.DOC (DOC_ID,DOC_UPLD_DT,DOC_CREATE_DT,MAIL_ROOM_PRSN_ID,DOC_CMPLTNS_IND,DOC_TYPE_CD,DOC_UPLD_RSN_CD,DOC_PAGE_CNT,SEND_MSGE_TO_RVW_IND,DOC_UPLD_MSGE_TXT,DOC_FILE_NM_TXT,DOC_FILE_LINK_TXT,DOC_ASSMNT_DT,LCTN_ID,DOC_MGMT_ID) values (201,to_date('19-JAN-16','DD-MON-RR'),null,null,'N','d','R',null,'Y',null,'Screenshot (1).png',null,null,null,'569ea9d7e1ac97e58d6bca1b');
Insert into ECRS.DOC (DOC_ID,DOC_UPLD_DT,DOC_CREATE_DT,MAIL_ROOM_PRSN_ID,DOC_CMPLTNS_IND,DOC_TYPE_CD,DOC_UPLD_RSN_CD,DOC_PAGE_CNT,SEND_MSGE_TO_RVW_IND,DOC_UPLD_MSGE_TXT,DOC_FILE_NM_TXT,DOC_FILE_LINK_TXT,DOC_ASSMNT_DT,LCTN_ID,DOC_MGMT_ID) values (221,to_date('20-JAN-16','DD-MON-RR'),null,null,'N','d','R',null,'Y',null,'Lancia Stratos.png',null,null,null,'569fa5cce4b0c074182b5e61');
Insert into ECRS.DOC (DOC_ID,DOC_UPLD_DT,DOC_CREATE_DT,MAIL_ROOM_PRSN_ID,DOC_CMPLTNS_IND,DOC_TYPE_CD,DOC_UPLD_RSN_CD,DOC_PAGE_CNT,SEND_MSGE_TO_RVW_IND,DOC_UPLD_MSGE_TXT,DOC_FILE_NM_TXT,DOC_FILE_LINK_TXT,DOC_ASSMNT_DT,LCTN_ID,DOC_MGMT_ID) values (241,to_date('21-JAN-16','DD-MON-RR'),null,null,'N','d','R',null,'Y',null,'Shuttle.jpg',null,null,null,'56a023ece4b0d586cbed1021');
Insert into ECRS.DOC (DOC_ID,DOC_UPLD_DT,DOC_CREATE_DT,MAIL_ROOM_PRSN_ID,DOC_CMPLTNS_IND,DOC_TYPE_CD,DOC_UPLD_RSN_CD,DOC_PAGE_CNT,SEND_MSGE_TO_RVW_IND,DOC_UPLD_MSGE_TXT,DOC_FILE_NM_TXT,DOC_FILE_LINK_TXT,DOC_ASSMNT_DT,LCTN_ID,DOC_MGMT_ID) values (303,to_date('25-JAN-16','DD-MON-RR'),null,null,'N','d','R',null,'Y',null,'gitignore_global.txt',null,null,null,'56a673c1e1ac4270fd26fc83');
Insert into ECRS.DOC (DOC_ID,DOC_UPLD_DT,DOC_CREATE_DT,MAIL_ROOM_PRSN_ID,DOC_CMPLTNS_IND,DOC_TYPE_CD,DOC_UPLD_RSN_CD,DOC_PAGE_CNT,SEND_MSGE_TO_RVW_IND,DOC_UPLD_MSGE_TXT,DOC_FILE_NM_TXT,DOC_FILE_LINK_TXT,DOC_ASSMNT_DT,LCTN_ID,DOC_MGMT_ID) values (305,to_date('25-JAN-16','DD-MON-RR'),null,null,'N','d','R',null,'Y',null,'Screenshot (1).png',null,null,null,'56a67436e1ac4270fd26fc85');
Insert into ECRS.DOC (DOC_ID,DOC_UPLD_DT,DOC_CREATE_DT,MAIL_ROOM_PRSN_ID,DOC_CMPLTNS_IND,DOC_TYPE_CD,DOC_UPLD_RSN_CD,DOC_PAGE_CNT,SEND_MSGE_TO_RVW_IND,DOC_UPLD_MSGE_TXT,DOC_FILE_NM_TXT,DOC_FILE_LINK_TXT,DOC_ASSMNT_DT,LCTN_ID,DOC_MGMT_ID) values (369,to_date('27-JAN-16','DD-MON-RR'),null,null,'N','d','R',null,'Y',null,'Screenshot (1).png',null,null,null,'56a8e731e4b0eb3d12fe31b1');
Insert into ECRS.DOC (DOC_ID,DOC_UPLD_DT,DOC_CREATE_DT,MAIL_ROOM_PRSN_ID,DOC_CMPLTNS_IND,DOC_TYPE_CD,DOC_UPLD_RSN_CD,DOC_PAGE_CNT,SEND_MSGE_TO_RVW_IND,DOC_UPLD_MSGE_TXT,DOC_FILE_NM_TXT,DOC_FILE_LINK_TXT,DOC_ASSMNT_DT,LCTN_ID,DOC_MGMT_ID) values (505,to_date('09-FEB-16','DD-MON-RR'),null,null,'N','d','R',null,'Y',null,'Screenshot (1).png',null,null,null,'56ba9ce36064391651e9bdcc');
Insert into ECRS.DOC (DOC_ID,DOC_UPLD_DT,DOC_CREATE_DT,MAIL_ROOM_PRSN_ID,DOC_CMPLTNS_IND,DOC_TYPE_CD,DOC_UPLD_RSN_CD,DOC_PAGE_CNT,SEND_MSGE_TO_RVW_IND,DOC_UPLD_MSGE_TXT,DOC_FILE_NM_TXT,DOC_FILE_LINK_TXT,DOC_ASSMNT_DT,LCTN_ID,DOC_MGMT_ID) values (522,to_date('10-FEB-16','DD-MON-RR'),null,null,'N','d','R',null,'Y',null,'Screenshot (1).png',null,null,null,'56bbe3f889eb85ebefab18f3');
Insert into ECRS.DOC (DOC_ID,DOC_UPLD_DT,DOC_CREATE_DT,MAIL_ROOM_PRSN_ID,DOC_CMPLTNS_IND,DOC_TYPE_CD,DOC_UPLD_RSN_CD,DOC_PAGE_CNT,SEND_MSGE_TO_RVW_IND,DOC_UPLD_MSGE_TXT,DOC_FILE_NM_TXT,DOC_FILE_LINK_TXT,DOC_ASSMNT_DT,LCTN_ID,DOC_MGMT_ID) values (529,to_date('11-FEB-16','DD-MON-RR'),null,null,'N','d','R',null,'Y',null,'Screenshot (1).png',null,null,null,'56bbfcaee4b0d80f48a67262');
Insert into ECRS.DOC (DOC_ID,DOC_UPLD_DT,DOC_CREATE_DT,MAIL_ROOM_PRSN_ID,DOC_CMPLTNS_IND,DOC_TYPE_CD,DOC_UPLD_RSN_CD,DOC_PAGE_CNT,SEND_MSGE_TO_RVW_IND,DOC_UPLD_MSGE_TXT,DOC_FILE_NM_TXT,DOC_FILE_LINK_TXT,DOC_ASSMNT_DT,LCTN_ID,DOC_MGMT_ID) values (553,to_date('11-FEB-16','DD-MON-RR'),null,null,'N','d','R',null,'Y',null,'Penguins.jpg',null,null,null,'56bd0c8853810250e8c1aabb');
Insert into ECRS.DOC (DOC_ID,DOC_UPLD_DT,DOC_CREATE_DT,MAIL_ROOM_PRSN_ID,DOC_CMPLTNS_IND,DOC_TYPE_CD,DOC_UPLD_RSN_CD,DOC_PAGE_CNT,SEND_MSGE_TO_RVW_IND,DOC_UPLD_MSGE_TXT,DOC_FILE_NM_TXT,DOC_FILE_LINK_TXT,DOC_ASSMNT_DT,LCTN_ID,DOC_MGMT_ID) values (555,to_date('11-FEB-16','DD-MON-RR'),null,null,'N','d','R',null,'Y',null,'Tulips.jpg',null,null,null,'56bd0cb053810250e8c1aabf');
Insert into ECRS.DOC (DOC_ID,DOC_UPLD_DT,DOC_CREATE_DT,MAIL_ROOM_PRSN_ID,DOC_CMPLTNS_IND,DOC_TYPE_CD,DOC_UPLD_RSN_CD,DOC_PAGE_CNT,SEND_MSGE_TO_RVW_IND,DOC_UPLD_MSGE_TXT,DOC_FILE_NM_TXT,DOC_FILE_LINK_TXT,DOC_ASSMNT_DT,LCTN_ID,DOC_MGMT_ID) values (559,to_date('11-FEB-16','DD-MON-RR'),null,null,'N','d','R',null,'Y',null,'Penguins.jpg',null,null,null,'56bd0e8b53810250e8c1aac8');
REM INSERTING into ECRS.DOC_UPLD_RSN_CD
SET DEFINE OFF;
Insert into ECRS.DOC_UPLD_RSN_CD (DOC_UPLD_RSN_CD,DOC_UPLD_RSN_CD_DESC) values ('R','Initial Upload');
Insert into ECRS.DOC_UPLD_RSN_CD (DOC_UPLD_RSN_CD,DOC_UPLD_RSN_CD_DESC) values ('A','Upload to Document Request');
REM INSERTING into ECRS.ETHNCTY_CD
SET DEFINE OFF;
Insert into ECRS.ETHNCTY_CD (ETHNCTY_CD,ETHNCTY_DESC) values ('1','Hispanic or Latino');
Insert into ECRS.ETHNCTY_CD (ETHNCTY_CD,ETHNCTY_DESC) values ('3','Not Furnished');
Insert into ECRS.ETHNCTY_CD (ETHNCTY_CD,ETHNCTY_DESC) values ('2','Not Hispanic or Latino');
REM INSERTING into ECRS.FHA_LOAN_TYPE_CD
SET DEFINE OFF;
Insert into ECRS.FHA_LOAN_TYPE_CD (FHA_LOAN_TYPE_CD,FHA_LOAN_TYPE_CD_DESC) values ('R','Refi');
Insert into ECRS.FHA_LOAN_TYPE_CD (FHA_LOAN_TYPE_CD,FHA_LOAN_TYPE_CD_DESC) values ('S','Purchase');
Insert into ECRS.FHA_LOAN_TYPE_CD (FHA_LOAN_TYPE_CD,FHA_LOAN_TYPE_CD_DESC) values ('N','Construction');
Insert into ECRS.FHA_LOAN_TYPE_CD (FHA_LOAN_TYPE_CD,FHA_LOAN_TYPE_CD_DESC) values ('H','HECM');
Insert into ECRS.FHA_LOAN_TYPE_CD (FHA_LOAN_TYPE_CD,FHA_LOAN_TYPE_CD_DESC) values ('K','203K');
REM INSERTING into ECRS.GNDR_CD
SET DEFINE OFF;
Insert into ECRS.GNDR_CD (GNDR_CD,GNDR_DESC) values ('M','Male');
Insert into ECRS.GNDR_CD (GNDR_CD,GNDR_DESC) values ('F','Female');
REM INSERTING into ECRS.HOC
SET DEFINE OFF;
Insert into ECRS.HOC (HOC_ID,HOC_NAME) values (1,'Atlanta');
Insert into ECRS.HOC (HOC_ID,HOC_NAME) values (2,'Denver');
Insert into ECRS.HOC (HOC_ID,HOC_NAME) values (3,'Sanata Ana');
Insert into ECRS.HOC (HOC_ID,HOC_NAME) values (4,'philadelphia');
Insert into ECRS.HOC (HOC_ID,HOC_NAME) values (5,'HUD HQ');
REM INSERTING into ECRS.HOC_EMPLYEE_ROLE_CD
SET DEFINE OFF;
Insert into ECRS.HOC_EMPLYEE_ROLE_CD (HOC_EMPLYEE_ROLE_CD,HOC_EMPLYEE_ROLE_CD_DESC) values ('R','HOC Basline Reviewer');
Insert into ECRS.HOC_EMPLYEE_ROLE_CD (HOC_EMPLYEE_ROLE_CD,HOC_EMPLYEE_ROLE_CD_DESC) values ('N','National Coordinator');
Insert into ECRS.HOC_EMPLYEE_ROLE_CD (HOC_EMPLYEE_ROLE_CD,HOC_EMPLYEE_ROLE_CD_DESC) values ('S','Supervisor');
REM INSERTING into ECRS.HOC_LOAN_RVW_PRSNL
SET DEFINE OFF;
Insert into ECRS.HOC_LOAN_RVW_PRSNL (ASGN_DT,HOC_EMPLYEE_ROLE_CD,LOAN_RVW_PRSNL_ID,HOC_ID) values (to_date('12-JAN-16','DD-MON-RR'),'R',1,1);
REM INSERTING into ECRS.HOC_RVW
SET DEFINE OFF;
Insert into ECRS.HOC_RVW (LRS_RVW_LVL_CD,LOAN_RVW_ID,LOAN_RVW_PRSNL_ID,HOC_ID,HOC_RVW_ID) values ('a',105,1,1,106);
Insert into ECRS.HOC_RVW (LRS_RVW_LVL_CD,LOAN_RVW_ID,LOAN_RVW_PRSNL_ID,HOC_ID,HOC_RVW_ID) values ('a',373,1,1,374);
Insert into ECRS.HOC_RVW (LRS_RVW_LVL_CD,LOAN_RVW_ID,LOAN_RVW_PRSNL_ID,HOC_ID,HOC_RVW_ID) values ('a',377,1,1,378);
Insert into ECRS.HOC_RVW (LRS_RVW_LVL_CD,LOAN_RVW_ID,LOAN_RVW_PRSNL_ID,HOC_ID,HOC_RVW_ID) values ('a',101,1,1,102);
Insert into ECRS.HOC_RVW (LRS_RVW_LVL_CD,LOAN_RVW_ID,LOAN_RVW_PRSNL_ID,HOC_ID,HOC_RVW_ID) values ('a',103,1,1,104);
Insert into ECRS.HOC_RVW (LRS_RVW_LVL_CD,LOAN_RVW_ID,LOAN_RVW_PRSNL_ID,HOC_ID,HOC_RVW_ID) values ('a',107,1,1,108);
Insert into ECRS.HOC_RVW (LRS_RVW_LVL_CD,LOAN_RVW_ID,LOAN_RVW_PRSNL_ID,HOC_ID,HOC_RVW_ID) values ('a',269,1,1,270);
Insert into ECRS.HOC_RVW (LRS_RVW_LVL_CD,LOAN_RVW_ID,LOAN_RVW_PRSNL_ID,HOC_ID,HOC_RVW_ID) values ('a',375,1,1,376);
REM INSERTING into ECRS.HOC_RVWR
SET DEFINE OFF;
REM INSERTING into ECRS.HOC_RVWR_AVLBLTY
SET DEFINE OFF;
REM INSERTING into ECRS.HOC_RVW_TEAM
SET DEFINE OFF;
REM INSERTING into ECRS.HOC_SPRVSR
SET DEFINE OFF;
REM INSERTING into ECRS.HOC_TEAM_LEAD
SET DEFINE OFF;
REM INSERTING into ECRS.HUD_RGN_FLD_OFC_CD
SET DEFINE OFF;
Insert into ECRS.HUD_RGN_FLD_OFC_CD (HUD_FLD_OFC_CD,HUD_RGN_CD,HUD_FLD_OFC_NM,HUD_FLD_OFC_ST_CD) values ('5 ','NE','New Mexico','NM');
REM INSERTING into ECRS.INSPCTR
SET DEFINE OFF;
Insert into ECRS.INSPCTR (INSPCTR_ID) values (4001);
Insert into ECRS.INSPCTR (INSPCTR_ID) values (4002);
REM INSERTING into ECRS.LNDR
SET DEFINE OFF;
Insert into ECRS.LNDR (SPNSR_LNDR_ID,PRE_CLSNG_PRCSNG_IND,LMTD_DNL_PRTCPTN_IND,HIGH_RISK_IND,HECM_PRE_CLSNG_IND,HECM_CRTFD_IND,HECM_CRTFD_DT,DRCT_ENDRSMNT_PRCSNG_IND,LNDR_CTGRY_CD,NMLS_ID,FHA_APRVL_DT,FY_END_NM,GNMA_ID,INCRPRTN_DT,INCRPRTN_ST_CD,TAX_IDNTFCTN_NBR,RCRTFCTN_ID,IMF_ID,LNDR_ID) values (1,'N','N','N','N','N',null,'N','1',null,null,null,null,null,null,920177969,null,null,1001);
REM INSERTING into ECRS.LNDR_RESP_LTR
SET DEFINE OFF;
REM INSERTING into ECRS.LOAN
SET DEFINE OFF;
Insert into ECRS.LOAN (LNDR_ID,SFH_PRPRTY_ID,HOC_ID,FHA_CASE_NBR,FHA_LOAN_TYPE_CD,FHA_LOAN_AMT,FHA_LOAN_TERM_NBR,INSPCTR_ID,APRSR_ID,PRJCTD_CLSNG_DT,HUD_RGN_CD,HUD_FLD_OFC_CD,LOAN_APPLCTN_CRTFCTN_IND,FHA_CASE_NBR_ASGNMT_DT,APRSL_RCVD_DT,LOAN_NOTE_TXT,BORRWR_ID,ADP_CD,LOAN_OFCR_ID,LOAN_ORGNTR_ID,LOAN_PRPS_CD,CASE_STAT_CD,CASE_STAT_DT,UNDRWRTNG_TYPE_CD,LOAN_INTRST_RATE_PCT,MORTG_PYMNT_PI_AMT,LNDR_CASE_RFRNC_CASE_NBR,LOAN_ID) values (1001,1,1,4833255554,'S',417000,360,4001,234,to_date('11-JAN-16','DD-MON-RR'),'NE','5 ','1',to_date('11-JAN-16','DD-MON-RR'),to_date('11-SEP-15','DD-MON-RR'),'SFSDF',1,'1  ',9001,1,'1','I',to_date('11-JAN-16','DD-MON-RR'),'S',1,2200,241256,1);
Insert into ECRS.LOAN (LNDR_ID,SFH_PRPRTY_ID,HOC_ID,FHA_CASE_NBR,FHA_LOAN_TYPE_CD,FHA_LOAN_AMT,FHA_LOAN_TERM_NBR,INSPCTR_ID,APRSR_ID,PRJCTD_CLSNG_DT,HUD_RGN_CD,HUD_FLD_OFC_CD,LOAN_APPLCTN_CRTFCTN_IND,FHA_CASE_NBR_ASGNMT_DT,APRSL_RCVD_DT,LOAN_NOTE_TXT,BORRWR_ID,ADP_CD,LOAN_OFCR_ID,LOAN_ORGNTR_ID,LOAN_PRPS_CD,CASE_STAT_CD,CASE_STAT_DT,UNDRWRTNG_TYPE_CD,LOAN_INTRST_RATE_PCT,MORTG_PYMNT_PI_AMT,LNDR_CASE_RFRNC_CASE_NBR,LOAN_ID) values (1001,1,2,3232288337,'R',234000,180,4001,235,to_date('11-JAN-16','DD-MON-RR'),'NE','5 ','1',to_date('14-JAN-16','DD-MON-RR'),to_date('14-SEP-15','DD-MON-RR'),'SFSDF',2,'2  ',9001,1,'1','I',to_date('11-JAN-16','DD-MON-RR'),'D',1,2100,346126,2);
Insert into ECRS.LOAN (LNDR_ID,SFH_PRPRTY_ID,HOC_ID,FHA_CASE_NBR,FHA_LOAN_TYPE_CD,FHA_LOAN_AMT,FHA_LOAN_TERM_NBR,INSPCTR_ID,APRSR_ID,PRJCTD_CLSNG_DT,HUD_RGN_CD,HUD_FLD_OFC_CD,LOAN_APPLCTN_CRTFCTN_IND,FHA_CASE_NBR_ASGNMT_DT,APRSL_RCVD_DT,LOAN_NOTE_TXT,BORRWR_ID,ADP_CD,LOAN_OFCR_ID,LOAN_ORGNTR_ID,LOAN_PRPS_CD,CASE_STAT_CD,CASE_STAT_DT,UNDRWRTNG_TYPE_CD,LOAN_INTRST_RATE_PCT,MORTG_PYMNT_PI_AMT,LNDR_CASE_RFRNC_CASE_NBR,LOAN_ID) values (1001,1,3,2611199228,'N',350000,120,4001,236,to_date('11-JAN-16','DD-MON-RR'),'NE','5 ','1',to_date('15-JAN-16','DD-MON-RR'),to_date('15-AUG-15','DD-MON-RR'),'SFSDF',3,'3  ',9001,1,'1','A',to_date('11-JAN-16','DD-MON-RR'),'S',1,2000,326457,3);
Insert into ECRS.LOAN (LNDR_ID,SFH_PRPRTY_ID,HOC_ID,FHA_CASE_NBR,FHA_LOAN_TYPE_CD,FHA_LOAN_AMT,FHA_LOAN_TERM_NBR,INSPCTR_ID,APRSR_ID,PRJCTD_CLSNG_DT,HUD_RGN_CD,HUD_FLD_OFC_CD,LOAN_APPLCTN_CRTFCTN_IND,FHA_CASE_NBR_ASGNMT_DT,APRSL_RCVD_DT,LOAN_NOTE_TXT,BORRWR_ID,ADP_CD,LOAN_OFCR_ID,LOAN_ORGNTR_ID,LOAN_PRPS_CD,CASE_STAT_CD,CASE_STAT_DT,UNDRWRTNG_TYPE_CD,LOAN_INTRST_RATE_PCT,MORTG_PYMNT_PI_AMT,LNDR_CASE_RFRNC_CASE_NBR,LOAN_ID) values (1001,1,4,2659871234,'H',150000,180,4001,237,to_date('11-JAN-16','DD-MON-RR'),'NE','5 ','1',to_date('23-JAN-16','DD-MON-RR'),to_date('23-OCT-15','DD-MON-RR'),'SFSDF',4,'4  ',9001,1,'1','C',to_date('11-JAN-16','DD-MON-RR'),'D',1,1500,463786,4);
Insert into ECRS.LOAN (LNDR_ID,SFH_PRPRTY_ID,HOC_ID,FHA_CASE_NBR,FHA_LOAN_TYPE_CD,FHA_LOAN_AMT,FHA_LOAN_TERM_NBR,INSPCTR_ID,APRSR_ID,PRJCTD_CLSNG_DT,HUD_RGN_CD,HUD_FLD_OFC_CD,LOAN_APPLCTN_CRTFCTN_IND,FHA_CASE_NBR_ASGNMT_DT,APRSL_RCVD_DT,LOAN_NOTE_TXT,BORRWR_ID,ADP_CD,LOAN_OFCR_ID,LOAN_ORGNTR_ID,LOAN_PRPS_CD,CASE_STAT_CD,CASE_STAT_DT,UNDRWRTNG_TYPE_CD,LOAN_INTRST_RATE_PCT,MORTG_PYMNT_PI_AMT,LNDR_CASE_RFRNC_CASE_NBR,LOAN_ID) values (1001,1,1,2361026321,'K',200000,360,4001,238,to_date('11-JAN-16','DD-MON-RR'),'NE','5 ','1',to_date('24-JAN-16','DD-MON-RR'),to_date('24-NOV-15','DD-MON-RR'),'SFSDF',5,'5  ',9001,1,'1','E',to_date('11-JAN-16','DD-MON-RR'),'S',1,1200,567893,5);
Insert into ECRS.LOAN (LNDR_ID,SFH_PRPRTY_ID,HOC_ID,FHA_CASE_NBR,FHA_LOAN_TYPE_CD,FHA_LOAN_AMT,FHA_LOAN_TERM_NBR,INSPCTR_ID,APRSR_ID,PRJCTD_CLSNG_DT,HUD_RGN_CD,HUD_FLD_OFC_CD,LOAN_APPLCTN_CRTFCTN_IND,FHA_CASE_NBR_ASGNMT_DT,APRSL_RCVD_DT,LOAN_NOTE_TXT,BORRWR_ID,ADP_CD,LOAN_OFCR_ID,LOAN_ORGNTR_ID,LOAN_PRPS_CD,CASE_STAT_CD,CASE_STAT_DT,UNDRWRTNG_TYPE_CD,LOAN_INTRST_RATE_PCT,MORTG_PYMNT_PI_AMT,LNDR_CASE_RFRNC_CASE_NBR,LOAN_ID) values (1001,1,2,2252376811,'S',99000,120,4001,239,to_date('11-JAN-16','DD-MON-RR'),'NE','5 ','1',to_date('25-JAN-16','DD-MON-RR'),to_date('20-SEP-15','DD-MON-RR'),'SFSDF',6,'6  ',9001,1,'1','I',to_date('11-JAN-16','DD-MON-RR'),'D',1,1000,678432,6);
Insert into ECRS.LOAN (LNDR_ID,SFH_PRPRTY_ID,HOC_ID,FHA_CASE_NBR,FHA_LOAN_TYPE_CD,FHA_LOAN_AMT,FHA_LOAN_TERM_NBR,INSPCTR_ID,APRSR_ID,PRJCTD_CLSNG_DT,HUD_RGN_CD,HUD_FLD_OFC_CD,LOAN_APPLCTN_CRTFCTN_IND,FHA_CASE_NBR_ASGNMT_DT,APRSL_RCVD_DT,LOAN_NOTE_TXT,BORRWR_ID,ADP_CD,LOAN_OFCR_ID,LOAN_ORGNTR_ID,LOAN_PRPS_CD,CASE_STAT_CD,CASE_STAT_DT,UNDRWRTNG_TYPE_CD,LOAN_INTRST_RATE_PCT,MORTG_PYMNT_PI_AMT,LNDR_CASE_RFRNC_CASE_NBR,LOAN_ID) values (1001,1,3,8081733562,'R',120000,180,4001,234,to_date('11-JAN-16','DD-MON-RR'),'NE','5 ','1',to_date('26-JAN-16','DD-MON-RR'),to_date('24-JUN-15','DD-MON-RR'),'SFSDF',7,'7  ',9001,1,'1','A',to_date('11-JAN-16','DD-MON-RR'),'S',1,1400,892456,7);
Insert into ECRS.LOAN (LNDR_ID,SFH_PRPRTY_ID,HOC_ID,FHA_CASE_NBR,FHA_LOAN_TYPE_CD,FHA_LOAN_AMT,FHA_LOAN_TERM_NBR,INSPCTR_ID,APRSR_ID,PRJCTD_CLSNG_DT,HUD_RGN_CD,HUD_FLD_OFC_CD,LOAN_APPLCTN_CRTFCTN_IND,FHA_CASE_NBR_ASGNMT_DT,APRSL_RCVD_DT,LOAN_NOTE_TXT,BORRWR_ID,ADP_CD,LOAN_OFCR_ID,LOAN_ORGNTR_ID,LOAN_PRPS_CD,CASE_STAT_CD,CASE_STAT_DT,UNDRWRTNG_TYPE_CD,LOAN_INTRST_RATE_PCT,MORTG_PYMNT_PI_AMT,LNDR_CASE_RFRNC_CASE_NBR,LOAN_ID) values (1001,1,5,3768543167,'N',160000,360,4001,236,to_date('11-JAN-16','DD-MON-RR'),'NE','5 ','1',to_date('28-JAN-16','DD-MON-RR'),to_date('26-DEC-15','DD-MON-RR'),'SFSDF',8,'8  ',9001,1,'1','C',to_date('11-JAN-16','DD-MON-RR'),'D',1,1800,426462,8);
REM INSERTING into ECRS.LOAN_BORRWR
SET DEFINE OFF;
Insert into ECRS.LOAN_BORRWR (BORRWR_ID,LOAN_ID) values (1,1);
Insert into ECRS.LOAN_BORRWR (BORRWR_ID,LOAN_ID) values (2,2);
Insert into ECRS.LOAN_BORRWR (BORRWR_ID,LOAN_ID) values (3,3);
Insert into ECRS.LOAN_BORRWR (BORRWR_ID,LOAN_ID) values (4,4);
Insert into ECRS.LOAN_BORRWR (BORRWR_ID,LOAN_ID) values (5,5);
Insert into ECRS.LOAN_BORRWR (BORRWR_ID,LOAN_ID) values (6,6);
Insert into ECRS.LOAN_BORRWR (BORRWR_ID,LOAN_ID) values (7,7);
Insert into ECRS.LOAN_BORRWR (BORRWR_ID,LOAN_ID) values (8,8);
REM INSERTING into ECRS.LOAN_FNDS_TO_CLS
SET DEFINE OFF;
Insert into ECRS.LOAN_FNDS_TO_CLS (INVSTMNT_RQRD_AMT,AST_AVLBL_TOT_AMT,BORRWR_CLSNG_COST_AMT,RSRV_MNTH_CNT,SLR_CNTRBTN_PCT,SLR_CNTRBTN_AMT,SALE_CNCSN_AMT,LOAN_ID) values (230000,230000,6000,1,1,1,1,1);
REM INSERTING into ECRS.LOAN_MORTG
SET DEFINE OFF;
Insert into ECRS.LOAN_MORTG (MORTG_WTHT_FNCL_LF_FEE_AMT,MORTG_WITH_FNCL_LF_FEE_AMT,MORTG_PYMNT_PI_AMT,PYMNTS_CURR_IND,LG_FEE_FNCL_IND,REAL_EST_TXS_AMT,HZRD_FLD_AMT,MORTG_OTHR_AMT,WRNTY_AMT,LOAN_TO_VAL_RATIO_PCT,CMBND_LOAN_TO_VAL_RATIO_PCT,PREM_MNTHLY_AMT,PREM_UPFRNT_RATE_PCT,PREM_ANUL_RATE_PCT,SALE_PRICE_AMT,APRSD_AMT,GRND_RENT_LEAN_FEE_AMT,UNPD_PRNCPL_BLNC_AMT,CURR_HUSNG_EXPNS_AMT,GRS_INCM_AMT,RQRMNT_TOT_AMT,FXD_PYMNT_TOT_AMT,FXD_DEBT_TO_INCM_RATIO_TOT_AMT,GRNTEE_FEE_PAID_IN_CASH_AMT,LOAN_ID) values (100,100,1000,'Y','N',4000,600,100,100,80,80,2500,4,4,250000,260000,1600,230000,1500,100000,90000,1600,60,2000,1);
REM INSERTING into ECRS.LOAN_OFCR
SET DEFINE OFF;
Insert into ECRS.LOAN_OFCR (LOAN_OFCR_ID,NMLS_ID,LNDR_ID) values (9001,1,1001);
REM INSERTING into ECRS.LOAN_ORGNTR
SET DEFINE OFF;
Insert into ECRS.LOAN_ORGNTR (LOAN_ORGNTR_ID,LOAN_ORGNTR_NAME,LOAN_ORGNTR_EMAIL) values (1,'J.P. MORGAN CHASE','lalith@nexxtek.com');
REM INSERTING into ECRS.LOAN_PRPS_CD
SET DEFINE OFF;
Insert into ECRS.LOAN_PRPS_CD (LOAN_PRPS_CD,LOAN_PRPS_CD_DESC) values ('1','Purchase');
REM INSERTING into ECRS.LOAN_RTNG
SET DEFINE OFF;
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('B2-A1-OYA-OK',308,to_date('25-JAN-16','DD-MON-RR'),to_date('25-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values (null,322,to_date('26-JAN-16','DD-MON-RR'),to_date('26-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('B2-A1-OYA-OK',329,to_date('26-JAN-16','DD-MON-RR'),to_date('26-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('B2-A1-OYA-OK',332,to_date('26-JAN-16','DD-MON-RR'),to_date('26-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('nullnullnullnullnullnullnullnullnullnullnullnull',333,to_date('26-JAN-16','DD-MON-RR'),to_date('26-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1C-T4-C
BI-2C-T4-U
null
7N1
7T2
7N3
7T4
7N5
7T6
7N7
7T8
7N9',347,to_date('26-JAN-16','DD-MON-RR'),to_date('26-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1C-T4-D
BI-2C-T4-C
null
7T1
7T2
7N3
7T4
7N5
7T6
7N7
7N8
7T9',349,to_date('26-JAN-16','DD-MON-RR'),to_date('26-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1C-T4-D
BI-2C-T4-C
null
7T1
7T2
7N3
7N4
7T5
7N6
7T7
7N8
7T9',351,to_date('26-JAN-16','DD-MON-RR'),to_date('26-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1C-T4-D
BI-2C-T4-C
null
7T1
7N2
7T3
7N4
7N5
7T6
7T7
7T8
7T9',352,to_date('26-JAN-16','DD-MON-RR'),to_date('26-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1C-T4-C
null
null
7T1
7N2
7T3
7N4
7T5
7N6
7T7
7N8
7T9',353,to_date('26-JAN-16','DD-MON-RR'),to_date('26-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1C-T4-D
BI-2C-T4-U
null
7T1
7T2
null
null
null
null
null
null
null',364,to_date('26-JAN-16','DD-MON-RR'),to_date('26-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1C-T4-C
BI-2C-T4-D
null
null
null
null
null
null
null
null
null
null',365,to_date('26-JAN-16','DD-MON-RR'),to_date('26-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1C-T4-D,BI-2C-T4-D,null,7T1,7T2,7T3,7T4,7T5,7T6,7T7,7T8,7T9',427,to_date('29-JAN-16','DD-MON-RR'),to_date('29-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1C-T4-D,BI-2C-T4-D,null,7T1,7T2,7T3,7T4,7T5,7T6,7T7,7T8,7T9',430,to_date('29-JAN-16','DD-MON-RR'),to_date('29-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1Q-T4-C,BI-2Q-T4-D,null,RF-Y1,RF-N2,RF-Y3,RF-N4,RF-Y5,RF-N6,RF-Y7,RF-N8,RF-Y9',457,to_date('02-FEB-16','DD-MON-RR'),to_date('02-FEB-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('B2-A1-OYA-OK',311,to_date('25-JAN-16','DD-MON-RR'),to_date('25-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('nullnullnullnullnullnullnullnullnullnullnullnull',331,to_date('26-JAN-16','DD-MON-RR'),to_date('26-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1C-T4-C
BI-2C-T4-D
null
7T1
7N2
7T3
7N4
7T5
7N6
7T7
7N8
7T9',345,to_date('26-JAN-16','DD-MON-RR'),to_date('26-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1C-T4-C
BI-2C-T4-D
null
7N1
7N2
7N3
7T4
7T5
7T6
7T7
7T8
7T9',348,to_date('26-JAN-16','DD-MON-RR'),to_date('26-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1C-T4-C
BI-2C-T4-D
null
7T1
7N2
7T3
7N4
7T5
7N6
7T7
7N8
7T9',350,to_date('26-JAN-16','DD-MON-RR'),to_date('26-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1C-T4-C
BI-2C-T4-D
null
7T1
7N2
7T3
7N4
7T5
7N6
7T7
7N8
7T9',401,to_date('28-JAN-16','DD-MON-RR'),to_date('28-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1C-T4-C,BI-2C-T4-D,null,7T1,7N2,7T3,7N4,7T5,7N6,7T7,7N8,7T9',402,to_date('28-JAN-16','DD-MON-RR'),to_date('28-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1C-T4-C,BI-2C-T4-D,null,7T1,7N2,7T3,7N4,7T5,7N6,7T7,7N8,7T9',403,to_date('28-JAN-16','DD-MON-RR'),to_date('28-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1C-T4-C,BI-2C-T4-D,null,7T1,7N2,7T3,7N4,7T5,7N6,7T7,7N8,7T9',406,to_date('28-JAN-16','DD-MON-RR'),to_date('28-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('null
null
null
null
null
null
null
null
null
null
null
null',408,to_date('28-JAN-16','DD-MON-RR'),to_date('28-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1C-T4-C,BI-2C-T4-D,null,7T1,7N2,7T3,7N4,7T5,7N6,7T7,7N8,7T9',421,to_date('29-JAN-16','DD-MON-RR'),to_date('29-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('null
BI-2C-T4-C
null
null
null
null
null
null
null
null
null
null',424,to_date('29-JAN-16','DD-MON-RR'),to_date('29-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1C-T4-C,BI-2C-T4-D,null,7N1,null,7T3,7N4,null,7T6,null,7N8,7T9',432,to_date('29-JAN-16','DD-MON-RR'),to_date('29-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1C-T4-C,BI-2C-T4-D,null,7N1,7N2,7T3,7N4,7N5,7T6,7N7,7N8,7T9',433,to_date('29-JAN-16','DD-MON-RR'),to_date('29-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1C-T4-C,BI-2C-T4-D,null,7N1,7N2,7T3,7N4,7N5,7T6,7N7,7N8,7T9',434,to_date('29-JAN-16','DD-MON-RR'),to_date('29-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1Q-T4-C,BI-2Q-T4-D,null,RF-Y1,RF-N2,RF-Y3,RF-N4,RF-Y5,RF-N6,RF-Y7,RF-N8,RF-Y9',439,to_date('29-JAN-16','DD-MON-RR'),to_date('29-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1Q-T4-C,BI-2Q-T4-D,null,RF-Y1,RF-N2,RF-Y3,RF-N4,RF-Y5,RF-N6,RF-Y7,RF-N8,RF-Y9',440,to_date('29-JAN-16','DD-MON-RR'),to_date('29-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('null,null,null,null,null,null,null,null,null,null,null,null',441,to_date('02-FEB-16','DD-MON-RR'),to_date('02-FEB-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1Q-T4-C,BI-2Q-T4-D,null,RF-Y1,RF-N2,RF-N3,RF-N4,RF-N5,RF-N6,RF-Y7,RF-N8,RF-N9',442,to_date('02-FEB-16','DD-MON-RR'),to_date('02-FEB-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1Q-T4-D,BI-2Q-T4-C,null,null,null,null,null,null,null,null,null,null',483,to_date('04-FEB-16','DD-MON-RR'),to_date('04-FEB-16','DD-MON-RR'),105);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('null,null,null,null,null,null,null,null,null,null,null,null',484,to_date('04-FEB-16','DD-MON-RR'),to_date('04-FEB-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('null,BI-2Q-T4-U,null,RF-Y1,null,null,null,null,null,null,null,null',485,to_date('04-FEB-16','DD-MON-RR'),to_date('04-FEB-16','DD-MON-RR'),373);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('null,BI-2Q-T4-C,null,null,null,null,null,null,null,null,null,null',486,to_date('04-FEB-16','DD-MON-RR'),to_date('04-FEB-16','DD-MON-RR'),105);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1Q-T4-C,BI-2Q-T4-D,null,RF-Y1,RF-N2,RF-Y3,RF-N4,RF-Y5,RF-N6,RF-Y7,RF-N8,RF-Y9',515,to_date('10-FEB-16','DD-MON-RR'),to_date('10-FEB-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('null9T4null7T1null7T3null7T2nullnullnullnull',336,to_date('26-JAN-16','DD-MON-RR'),to_date('26-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1C-T4-C
BI-2C-T4-D
null
7T1
7N2
7T3
7N4
7T5
7N6
7T7
7N8
7T9',346,to_date('26-JAN-16','DD-MON-RR'),to_date('26-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1C-T4-C
BI-2C-T4-D
null
7T1
7N2
7T3
7N4
7T5
7N6
7T7
7N8
7T9',381,to_date('27-JAN-16','DD-MON-RR'),to_date('27-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('null
null
null
null
null
null
null
null
null
null
null
null',409,to_date('28-JAN-16','DD-MON-RR'),to_date('28-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1C-T4-C,BI-2C-T4-D,null,7T1,7N2,7T3,7N4,7T5,7N6,7T7,7N8,7T9',423,to_date('29-JAN-16','DD-MON-RR'),to_date('29-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1C-T4-D,BI-2C-T4-D,null,7T1,7T2,7T3,7T4,7T5,7T6,7T7,7T8,7T9',428,to_date('29-JAN-16','DD-MON-RR'),to_date('29-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1C-T4-D,BI-2C-T4-D,null,7T1,7T2,7T3,7T4,7T5,7T6,7T7,7T8,7T9',429,to_date('29-JAN-16','DD-MON-RR'),to_date('29-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1C-T4-C,BI-2C-T4-D,null,7T1,7N2,7T3,7N4,7T5,7N6,7T7,7N8,7T9',431,to_date('29-JAN-16','DD-MON-RR'),to_date('29-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1Q-T4-C,BI-2Q-T4-D,null,RF-Y1,RF-N2,RF-Y3,RF-N4,RF-Y5,RF-N6,RF-Y7,RF-N8,RF-Y9',437,to_date('29-JAN-16','DD-MON-RR'),to_date('29-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1Q-T4-C,BI-2Q-T4-C,null,RF-Y1,RF-Y2,RF-Y3,RF-Y4,RF-Y5,RF-Y6,RF-Y7,RF-Y8,RF-Y9',450,to_date('02-FEB-16','DD-MON-RR'),to_date('02-FEB-16','DD-MON-RR'),375);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1Q-T4-C,BI-2Q-T4-D,null,RF-Y1,RF-N2,RF-Y3,RF-N4,RF-Y5,RF-N6,RF-Y7,RF-N8,RF-Y9',460,to_date('02-FEB-16','DD-MON-RR'),to_date('02-FEB-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1Q-T4-C,BI-2Q-T4-D,null,RF-Y1,RF-N2,RF-Y3,RF-N4,RF-Y5,RF-N6,RF-Y7,RF-N8,RF-Y9',463,to_date('02-FEB-16','DD-MON-RR'),to_date('02-FEB-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1Q-T4-C,BI-2Q-T4-D,null,RF-Y1,RF-N2,RF-N3,RF-N4,RF-Y5,RF-N6,RF-Y7,RF-Y8,RF-Y9',482,to_date('04-FEB-16','DD-MON-RR'),to_date('04-FEB-16','DD-MON-RR'),105);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1Q-T4-C,BI-2Q-T4-D,null,RF-Y1,RF-N2,RF-Y3,RF-N4,RF-Y5,RF-N6,RF-Y7,RF-N8,RF-Y9',563,to_date('12-FEB-16','DD-MON-RR'),to_date('12-FEB-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('B2-A1-OYA-OK',307,to_date('25-JAN-16','DD-MON-RR'),to_date('25-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('B2-A1-OYA-OK',312,to_date('25-JAN-16','DD-MON-RR'),to_date('25-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('B2-A1-OYA-OK',313,to_date('25-JAN-16','DD-MON-RR'),to_date('25-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('N',314,to_date('25-JAN-16','DD-MON-RR'),to_date('25-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values (null,315,to_date('25-JAN-16','DD-MON-RR'),to_date('25-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('B2-A1-OYA-OK',316,to_date('25-JAN-16','DD-MON-RR'),to_date('25-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('B2-A1-OYA-OK',317,to_date('25-JAN-16','DD-MON-RR'),to_date('25-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('B2-A1-OYA-OK',320,to_date('25-JAN-16','DD-MON-RR'),to_date('25-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values (null,321,to_date('25-JAN-16','DD-MON-RR'),to_date('25-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('8T4',323,to_date('26-JAN-16','DD-MON-RR'),to_date('26-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('8T4nullnullnullnullnullnullnullnullnullnullnull',324,to_date('26-JAN-16','DD-MON-RR'),to_date('26-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1C-T4-Cnullnullnullnullnullnullnullnullnullnullnull',325,to_date('26-JAN-16','DD-MON-RR'),to_date('26-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('nullnullnullnullnullnullnullnullnullnullnullnull',326,to_date('26-JAN-16','DD-MON-RR'),to_date('26-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1C-T4-C
BI-2C-T4-D
null
7T1
7N2
7T3
7N4
7T5
7N6
7T7
7N8
7T9',354,to_date('26-JAN-16','DD-MON-RR'),to_date('26-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('null
null
null
null
null
null
null
null
null
null
null
null',366,to_date('26-JAN-16','DD-MON-RR'),to_date('26-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('null
null
null
null
null
null
null
null
null
null
null
null',367,to_date('26-JAN-16','DD-MON-RR'),to_date('26-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1C-T4-C
BI-2C-T4-D
null
7N1
7N2
null
7N4
7N5
7N6
null
7N8
7N9',368,to_date('26-JAN-16','DD-MON-RR'),to_date('26-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1C-T4-C
BI-2C-T4-D
null
7T1
7N2
7T3
7N4
7T5
7N6
7T7
7N8
7T9',371,to_date('27-JAN-16','DD-MON-RR'),to_date('27-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1C-T4-C
BI-2C-T4-D
null
7T1
7N2
7T3
7N4
7T5
7N6
7T7
7N8
7T9',372,to_date('27-JAN-16','DD-MON-RR'),to_date('27-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('null
null
null
null
null
null
null
null
null
null
null
null',407,to_date('28-JAN-16','DD-MON-RR'),to_date('28-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1C-T4-C,BI-2C-T4-D,null,7T1,7N2,7T3,7N4,7T5,7N6,7T7,7N8,7T9',410,to_date('28-JAN-16','DD-MON-RR'),to_date('28-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1C-T4-C,BI-2C-T4-D,null,7N1,7N2,7N3,7T4,7T5,7T6,7N7,7T8,7N9',422,to_date('29-JAN-16','DD-MON-RR'),to_date('29-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('null,null,null,null,null,null,null,null,null,null,null,null',426,to_date('29-JAN-16','DD-MON-RR'),to_date('29-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1Q-T4-C,BI-2Q-T4-D,null,RF-Y1,RF-N2,RF-Y3,RF-N4,RF-Y5,RF-N6,RF-Y7,RF-N8,RF-Y9',449,to_date('02-FEB-16','DD-MON-RR'),to_date('02-FEB-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1Q-T4-C,BI-2Q-T4-D,null,null,null,null,null,null,RF-Y6,null,RF-Y8,null',464,to_date('03-FEB-16','DD-MON-RR'),to_date('03-FEB-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('null,null,null,null,null,null,null,null,null,null,null,null',481,to_date('04-FEB-16','DD-MON-RR'),to_date('04-FEB-16','DD-MON-RR'),105);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('B1-B2-B3-I1',282,to_date('22-JAN-16','DD-MON-RR'),to_date('22-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('B2-A1-OYA-OK',283,to_date('22-JAN-16','DD-MON-RR'),to_date('22-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('B2-A1-OYA-OK',309,to_date('25-JAN-16','DD-MON-RR'),to_date('25-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('B2-A1-OYA-OK',310,to_date('25-JAN-16','DD-MON-RR'),to_date('25-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('B2-A1-OYA-OK',318,to_date('25-JAN-16','DD-MON-RR'),to_date('25-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('B2-A1-OYA-OK',319,to_date('25-JAN-16','DD-MON-RR'),to_date('25-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('nullnullnullnullnullnullnullnullnullnullnullnull',327,to_date('26-JAN-16','DD-MON-RR'),to_date('26-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('nullnullnullnullnullnullnullnullnullnullnullnull',328,to_date('26-JAN-16','DD-MON-RR'),to_date('26-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('B2-A1-OYA-OK',330,to_date('26-JAN-16','DD-MON-RR'),to_date('26-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('nullnullnullnullnullnullnullnullnullnullnullnull',334,to_date('26-JAN-16','DD-MON-RR'),to_date('26-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('B2-A1-OYA-OK',335,to_date('26-JAN-16','DD-MON-RR'),to_date('26-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1C-T4-C9T2null7T1null7T3null7T2nullnullnullnull',337,to_date('26-JAN-16','DD-MON-RR'),to_date('26-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1C-T4-C
9T2
null
7T1
null
7T3
null
7T2
null
null
null
null',341,to_date('26-JAN-16','DD-MON-RR'),to_date('26-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1C-T4-C9T2nullnullnull7T37T2nullnullnullnullnull',342,to_date('26-JAN-16','DD-MON-RR'),to_date('26-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1C-T4-C
9T2
null
7T1
null
7T3
null
7T2
null
null
null
null',343,to_date('26-JAN-16','DD-MON-RR'),to_date('26-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('8T29T4null7T17T27T37T27T2nullnullnullnull',344,to_date('26-JAN-16','DD-MON-RR'),to_date('26-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1C-T4-D
BI-2C-T4-D
null
7N1
7N2
7N3
7N4
7N5
7N6
7N7
7N8
7N9',361,to_date('26-JAN-16','DD-MON-RR'),to_date('26-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1C-T4-D
BI-2C-T4-U
null
7N1
7N2
7N3
null
7N5
null
7N7
7N8
7N9',362,to_date('26-JAN-16','DD-MON-RR'),to_date('26-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1C-T4-D
BI-2C-T4-D
null
7N1
7N2
7N3
7N4
7N5
7N6
7N7
7N8
7N9',363,to_date('26-JAN-16','DD-MON-RR'),to_date('26-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1C-T4-C,BI-2C-T4-D,null,7T1,7N2,7T3,7N4,7T5,7N6,7T7,7N8,7T9',425,to_date('29-JAN-16','DD-MON-RR'),to_date('29-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1Q-T4-C,BI-2Q-T4-D,null,RF-Y1,RF-N2,RF-Y3,RF-N4,RF-Y5,RF-N6,RF-Y7,RF-N8,RF-Y9',438,to_date('29-JAN-16','DD-MON-RR'),to_date('29-JAN-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1Q-T4-C,BI-2Q-T4-D,null,RF-Y1,RF-N2,RF-Y3,RF-N4,RF-Y5,RF-N6,RF-Y7,RF-N8,RF-Y9',528,to_date('11-FEB-16','DD-MON-RR'),to_date('11-FEB-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1Q-T4-C,BI-2Q-T4-D,null,RF-Y1,RF-N2,RF-Y3,RF-N4,RF-Y5,RF-N6,RF-Y7,RF-N8,RF-Y9',535,to_date('11-FEB-16','DD-MON-RR'),to_date('11-FEB-16','DD-MON-RR'),101);
Insert into ECRS.LOAN_RTNG (LOAN_RTNG_VALUE,LOAN_RTNG_ID,LOAN_RTNG_DT,LOAN_RTNG_LTR_SENT_DT,LOAN_RVW_ID) values ('BI-1Q-T4-C,BI-2Q-T4-D,null,RF-N1,RF-Y2,RF-N3,RF-Y4,RF-N5,RF-N6,RF-Y7,RF-N8,RF-Y9',581,to_date('16-FEB-16','DD-MON-RR'),to_date('16-FEB-16','DD-MON-RR'),107);
REM INSERTING into ECRS.LOAN_RVW
SET DEFINE OFF;
Insert into ECRS.LOAN_RVW (LRS_RVW_TYPE_CD,SLCTN_SRC_CD,LRS_STAT_CD,LOAN_RVW_ID,LOAN_ID) values ('R','R','E',105,3);
Insert into ECRS.LOAN_RVW (LRS_RVW_TYPE_CD,SLCTN_SRC_CD,LRS_STAT_CD,LOAN_RVW_ID,LOAN_ID) values ('L','R','E',373,6);
Insert into ECRS.LOAN_RVW (LRS_RVW_TYPE_CD,SLCTN_SRC_CD,LRS_STAT_CD,LOAN_RVW_ID,LOAN_ID) values ('S','R','I',377,8);
Insert into ECRS.LOAN_RVW (LRS_RVW_TYPE_CD,SLCTN_SRC_CD,LRS_STAT_CD,LOAN_RVW_ID,LOAN_ID) values ('P','R','E',101,1);
Insert into ECRS.LOAN_RVW (LRS_RVW_TYPE_CD,SLCTN_SRC_CD,LRS_STAT_CD,LOAN_RVW_ID,LOAN_ID) values ('D','R','I',103,2);
Insert into ECRS.LOAN_RVW (LRS_RVW_TYPE_CD,SLCTN_SRC_CD,LRS_STAT_CD,LOAN_RVW_ID,LOAN_ID) values ('M','R','E',107,4);
Insert into ECRS.LOAN_RVW (LRS_RVW_TYPE_CD,SLCTN_SRC_CD,LRS_STAT_CD,LOAN_RVW_ID,LOAN_ID) values ('C','R','I',269,5);
Insert into ECRS.LOAN_RVW (LRS_RVW_TYPE_CD,SLCTN_SRC_CD,LRS_STAT_CD,LOAN_RVW_ID,LOAN_ID) values ('R','R','I',375,7);
REM INSERTING into ECRS.LOAN_RVW_ACTVTY
SET DEFINE OFF;
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('25-JAN-16','DD-MON-RR'),null,null,'R',301,302,'1-ACT817',to_date('30-JAN-16','DD-MON-RR'),1,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('11-FEB-16','DD-MON-RR'),null,null,'R',543,544,'1-ACT966',to_date('16-FEB-16','DD-MON-RR'),1,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('11-FEB-16','DD-MON-RR'),null,null,'R',545,546,'1-ACT275',to_date('16-FEB-16','DD-MON-RR'),1,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('11-FEB-16','DD-MON-RR'),null,null,'R',547,548,'1-ACT219',to_date('16-FEB-16','DD-MON-RR'),1,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('11-FEB-16','DD-MON-RR'),null,null,'R',557,558,'1-ACT588',to_date('16-FEB-16','DD-MON-RR'),1,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('29-JAN-16','DD-MON-RR'),null,null,'R',435,438,'1-ACT503',to_date('03-FEB-16','DD-MON-RR'),4,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('02-FEB-16','DD-MON-RR'),null,null,'R',447,448,'1-ACT638',to_date('07-FEB-16','DD-MON-RR'),1,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('02-FEB-16','DD-MON-RR'),null,null,'R',451,452,'1-ACT394',to_date('07-FEB-16','DD-MON-RR'),1,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('02-FEB-16','DD-MON-RR'),null,null,'R',453,454,'1-ACT168',to_date('07-FEB-16','DD-MON-RR'),1,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('29-JAN-16','DD-MON-RR'),null,null,'U',435,453,'1-ACT503',to_date('03-FEB-16','DD-MON-RR'),8,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('02-FEB-16','DD-MON-RR'),null,null,'R',461,462,'1-ACT838',to_date('07-FEB-16','DD-MON-RR'),1,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('29-JAN-16','DD-MON-RR'),null,null,'R',435,439,'1-ACT503',to_date('03-FEB-16','DD-MON-RR'),5,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('09-FEB-16','DD-MON-RR'),null,null,'R',501,502,'1-ACT678',to_date('14-FEB-16','DD-MON-RR'),1,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('29-JAN-16','DD-MON-RR'),null,null,'R',435,440,'1-ACT503',to_date('03-FEB-16','DD-MON-RR'),6,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('29-JAN-16','DD-MON-RR'),null,null,'R',435,441,'1-ACT503',to_date('03-FEB-16','DD-MON-RR'),7,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('09-FEB-16','DD-MON-RR'),null,null,'R',503,504,'1-ACT856',to_date('14-FEB-16','DD-MON-RR'),1,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('29-JAN-16','DD-MON-RR'),null,null,'R',435,442,'1-ACT503',to_date('03-FEB-16','DD-MON-RR'),8,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('09-FEB-16','DD-MON-RR'),null,null,'R',507,508,'1-ACT69',to_date('14-FEB-16','DD-MON-RR'),1,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('09-FEB-16','DD-MON-RR'),null,null,'R',509,510,'1-ACT232',to_date('14-FEB-16','DD-MON-RR'),1,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('19-JAN-16','DD-MON-RR'),null,null,'U',197,198,'1-ACT262',to_date('24-JAN-16','DD-MON-RR'),1,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('21-JAN-16','DD-MON-RR'),null,null,'U',261,262,'1-ACT868',to_date('26-JAN-16','DD-MON-RR'),1,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('21-JAN-16','DD-MON-RR'),null,null,'D',263,264,'1-ACT445',to_date('26-JAN-16','DD-MON-RR'),1,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('21-JAN-16','DD-MON-RR'),null,null,'R',265,266,'1-ACT920',to_date('26-JAN-16','DD-MON-RR'),1,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('21-JAN-16','DD-MON-RR'),null,null,'U',267,268,'1-ACT504',to_date('26-JAN-16','DD-MON-RR'),1,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('28-JAN-16','DD-MON-RR'),null,null,'D',404,405,'1-ACT373',to_date('02-FEB-16','DD-MON-RR'),1,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('29-JAN-16','DD-MON-RR'),null,null,'R',435,437,'1-ACT503',to_date('03-FEB-16','DD-MON-RR'),1,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('27-JAN-16','DD-MON-RR'),null,null,'U',369,372,'1-ACT260',to_date('01-FEB-16','DD-MON-RR'),2,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('27-JAN-16','DD-MON-RR'),null,null,'U',369,373,'1-ACT261',to_date('01-FEB-16','DD-MON-RR'),2,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('02-FEB-16','DD-MON-RR'),null,null,'R',445,446,'1-ACT100',to_date('07-FEB-16','DD-MON-RR'),1,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('10-FEB-16','DD-MON-RR'),null,null,'R',513,514,'1-ACT998',to_date('15-FEB-16','DD-MON-RR'),5,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('10-FEB-16','DD-MON-RR'),null,null,'R',516,517,'1-ACT914',to_date('15-FEB-16','DD-MON-RR'),1,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('11-FEB-16','DD-MON-RR'),null,null,'R',524,525,'1-ACT276',to_date('16-FEB-16','DD-MON-RR'),1,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('11-FEB-16','DD-MON-RR'),null,null,'R',526,527,'1-ACT935',to_date('16-FEB-16','DD-MON-RR'),1,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('11-FEB-16','DD-MON-RR'),null,null,'R',561,562,'1-ACT228',to_date('16-FEB-16','DD-MON-RR'),1,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('21-JAN-16','DD-MON-RR'),null,null,'U',271,272,'1-ACT817',to_date('26-JAN-16','DD-MON-RR'),3,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('02-FEB-16','DD-MON-RR'),null,null,'R',443,444,'1-ACT844',to_date('07-FEB-16','DD-MON-RR'),1,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('02-FEB-16','DD-MON-RR'),null,null,'R',455,456,'1-ACT261',to_date('07-FEB-16','DD-MON-RR'),1,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('02-FEB-16','DD-MON-RR'),null,null,'R',458,459,'1-ACT81',to_date('07-FEB-16','DD-MON-RR'),1,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('10-FEB-16','DD-MON-RR'),null,null,'R',511,512,'1-ACT257',to_date('15-FEB-16','DD-MON-RR'),4,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('10-FEB-16','DD-MON-RR'),null,null,'R',518,519,'1-ACT804',to_date('15-FEB-16','DD-MON-RR'),1,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('10-FEB-16','DD-MON-RR'),null,null,'R',520,521,'1-ACT923',to_date('15-FEB-16','DD-MON-RR'),1,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('11-FEB-16','DD-MON-RR'),null,null,'R',531,532,'1-ACT251',to_date('16-FEB-16','DD-MON-RR'),1,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('11-FEB-16','DD-MON-RR'),null,null,'R',533,534,'1-ACT399',to_date('16-FEB-16','DD-MON-RR'),1,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('11-FEB-16','DD-MON-RR'),null,null,'R',541,542,'1-ACT466',to_date('16-FEB-16','DD-MON-RR'),1,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('11-FEB-16','DD-MON-RR'),null,null,'R',549,550,'1-ACT219',to_date('16-FEB-16','DD-MON-RR'),1,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('11-FEB-16','DD-MON-RR'),null,null,'R',551,552,'1-ACT250',to_date('16-FEB-16','DD-MON-RR'),1,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('19-JAN-16','DD-MON-RR'),null,null,'U',193,194,'1-ACT521',to_date('24-JAN-16','DD-MON-RR'),1,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('19-JAN-16','DD-MON-RR'),null,null,'U',199,200,'1-ACT111',to_date('24-JAN-16','DD-MON-RR'),1,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('19-JAN-16','DD-MON-RR'),null,null,'U',201,202,'1-ACT758',to_date('24-JAN-16','DD-MON-RR'),1,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('20-JAN-16','DD-MON-RR'),null,null,'U',221,222,'1-ACT29',to_date('25-JAN-16','DD-MON-RR'),1,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('21-JAN-16','DD-MON-RR'),null,null,'U',241,242,'1-ACT757',to_date('26-JAN-16','DD-MON-RR'),1,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('25-JAN-16','DD-MON-RR'),null,null,'U',303,304,'1-ACT296',to_date('30-JAN-16','DD-MON-RR'),1,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('25-JAN-16','DD-MON-RR'),null,null,'U',305,306,'1-ACT268',to_date('30-JAN-16','DD-MON-RR'),1,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('27-JAN-16','DD-MON-RR'),null,null,'U',369,370,'1-ACT884',to_date('01-FEB-16','DD-MON-RR'),1,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('09-FEB-16','DD-MON-RR'),null,null,'R',505,506,'1-ACT94',to_date('14-FEB-16','DD-MON-RR'),1,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('10-FEB-16','DD-MON-RR'),null,null,'R',522,523,'1-ACT900',to_date('15-FEB-16','DD-MON-RR'),1,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('11-FEB-16','DD-MON-RR'),null,null,'R',529,530,'1-ACT546',to_date('16-FEB-16','DD-MON-RR'),1,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('11-FEB-16','DD-MON-RR'),null,null,'R',553,554,'1-ACT568',to_date('16-FEB-16','DD-MON-RR'),1,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('11-FEB-16','DD-MON-RR'),null,null,'R',555,556,'1-ACT690',to_date('16-FEB-16','DD-MON-RR'),1,'Uploaded');
Insert into ECRS.LOAN_RVW_ACTVTY (ACTVTY_STRT_DT,ACTVTY_END_DT,LOAN_RVW_ID,ACTVTY_RSN_CD,DOC_ID,LOAN_RVW_ACTVTY_ID,ACTVTY_ID,ACTVTY_DUE_DT,LOAN_ID,ACTVTY_STAT) values (to_date('11-FEB-16','DD-MON-RR'),null,null,'R',559,560,'1-ACT397',to_date('16-FEB-16','DD-MON-RR'),1,'Uploaded');
REM INSERTING into ECRS.LOAN_RVW_PRSNL
SET DEFINE OFF;
Insert into ECRS.LOAN_RVW_PRSNL (LOAN_RVW_PRSNL_ID) values (1);
REM INSERTING into ECRS.LOAN_RVW_SKL_MTRX
SET DEFINE OFF;
REM INSERTING into ECRS.LRS_DFCNCY
SET DEFINE OFF;
REM INSERTING into ECRS.LRS_DFCNCY_CTGRY_CD
SET DEFINE OFF;
REM INSERTING into ECRS.LRS_DFCNCY_CTGRY_TYPE
SET DEFINE OFF;
REM INSERTING into ECRS.LRS_DFCNCY_LTR
SET DEFINE OFF;
REM INSERTING into ECRS.LRS_DFCNCY_TYPE_CD
SET DEFINE OFF;
REM INSERTING into ECRS.LRS_RVW_LVL_CD
SET DEFINE OFF;
Insert into ECRS.LRS_RVW_LVL_CD (LRS_RVW_LVL_CD,LRS_RVW_LVL_CD_DESC) values ('1',null);
Insert into ECRS.LRS_RVW_LVL_CD (LRS_RVW_LVL_CD,LRS_RVW_LVL_CD_DESC) values ('a',null);
REM INSERTING into ECRS.LRS_RVW_TYPE_CD
SET DEFINE OFF;
Insert into ECRS.LRS_RVW_TYPE_CD (LRS_RVW_TYPE_CD,LRS_RVW_TYPE_CD_DESC) values ('R','Post Endorsment Technical Review');
Insert into ECRS.LRS_RVW_TYPE_CD (LRS_RVW_TYPE_CD,LRS_RVW_TYPE_CD_DESC) values ('L','Lender Monitoring Loan Reviews');
Insert into ECRS.LRS_RVW_TYPE_CD (LRS_RVW_TYPE_CD,LRS_RVW_TYPE_CD_DESC) values ('S','Lender Self Report');
Insert into ECRS.LRS_RVW_TYPE_CD (LRS_RVW_TYPE_CD,LRS_RVW_TYPE_CD_DESC) values ('P','OIG');
Insert into ECRS.LRS_RVW_TYPE_CD (LRS_RVW_TYPE_CD,LRS_RVW_TYPE_CD_DESC) values ('D','FHA');
Insert into ECRS.LRS_RVW_TYPE_CD (LRS_RVW_TYPE_CD,LRS_RVW_TYPE_CD_DESC) values ('M','Post Mortem');
Insert into ECRS.LRS_RVW_TYPE_CD (LRS_RVW_TYPE_CD,LRS_RVW_TYPE_CD_DESC) values ('C','Conditional');
REM INSERTING into ECRS.LRS_STAT_CD
SET DEFINE OFF;
Insert into ECRS.LRS_STAT_CD (LRS_STAT_CD,LRS_STAT_CD_DESC) values ('R','In Review');
Insert into ECRS.LRS_STAT_CD (LRS_STAT_CD,LRS_STAT_CD_DESC) values ('I','In Progress');
Insert into ECRS.LRS_STAT_CD (LRS_STAT_CD,LRS_STAT_CD_DESC) values ('E','E-Mail Sent');
REM INSERTING into ECRS.MRTL_STAT_CD
SET DEFINE OFF;
Insert into ECRS.MRTL_STAT_CD (MRTL_STAT_CD,MRTL_STAT_CD_DESC) values ('M','Married');
Insert into ECRS.MRTL_STAT_CD (MRTL_STAT_CD,MRTL_STAT_CD_DESC) values ('S','Single');
Insert into ECRS.MRTL_STAT_CD (MRTL_STAT_CD,MRTL_STAT_CD_DESC) values ('D','Divorced');
Insert into ECRS.MRTL_STAT_CD (MRTL_STAT_CD,MRTL_STAT_CD_DESC) values ('W','Widowed');
REM INSERTING into ECRS.POST_ENDRSMNT_TECH_RVW
SET DEFINE OFF;
REM INSERTING into ECRS.PRSN
SET DEFINE OFF;
Insert into ECRS.PRSN (PRSN_DOB_DT,PRSN_SSN,GNDR_CD,ETHNCTY_CD,PRSN_DSBLD_IND,PRSN_STDNT_IND,PRSN_REC_EFCTV_DT,HOME_PHNE_NUM,WORK_PHNE_NUM,ADDR_ID,PRSN_ID,PRSN_TYPE_CD,FAX_PHN_NBR,FRST_NM,MDL_NM,LAST_NM,NM_SFX_CD,MRTL_STAT_CD) values (to_date('19-JAN-16','DD-MON-RR'),'223197864','M','2','N','N',null,null,null,null,1,'B',null,'Bill','B','Gates',null,'M');
Insert into ECRS.PRSN (PRSN_DOB_DT,PRSN_SSN,GNDR_CD,ETHNCTY_CD,PRSN_DSBLD_IND,PRSN_STDNT_IND,PRSN_REC_EFCTV_DT,HOME_PHNE_NUM,WORK_PHNE_NUM,ADDR_ID,PRSN_ID,PRSN_TYPE_CD,FAX_PHN_NBR,FRST_NM,MDL_NM,LAST_NM,NM_SFX_CD,MRTL_STAT_CD) values (to_date('19-JAN-16','DD-MON-RR'),'387164753','F','2','N','N',null,null,null,null,9001,'O',null,'Johnny','E','Banker',null,'M');
Insert into ECRS.PRSN (PRSN_DOB_DT,PRSN_SSN,GNDR_CD,ETHNCTY_CD,PRSN_DSBLD_IND,PRSN_STDNT_IND,PRSN_REC_EFCTV_DT,HOME_PHNE_NUM,WORK_PHNE_NUM,ADDR_ID,PRSN_ID,PRSN_TYPE_CD,FAX_PHN_NBR,FRST_NM,MDL_NM,LAST_NM,NM_SFX_CD,MRTL_STAT_CD) values (to_date('19-JAN-16','DD-MON-RR'),'381764634','F','2','N','N',null,null,null,null,4,'B',null,'Angelina','A','Jolie',null,'M');
Insert into ECRS.PRSN (PRSN_DOB_DT,PRSN_SSN,GNDR_CD,ETHNCTY_CD,PRSN_DSBLD_IND,PRSN_STDNT_IND,PRSN_REC_EFCTV_DT,HOME_PHNE_NUM,WORK_PHNE_NUM,ADDR_ID,PRSN_ID,PRSN_TYPE_CD,FAX_PHN_NBR,FRST_NM,MDL_NM,LAST_NM,NM_SFX_CD,MRTL_STAT_CD) values (to_date('19-JAN-16','DD-MON-RR'),'381764634','F','2','N','N',null,null,null,null,5,'B',null,'Angelina','A','Jolie',null,'M');
Insert into ECRS.PRSN (PRSN_DOB_DT,PRSN_SSN,GNDR_CD,ETHNCTY_CD,PRSN_DSBLD_IND,PRSN_STDNT_IND,PRSN_REC_EFCTV_DT,HOME_PHNE_NUM,WORK_PHNE_NUM,ADDR_ID,PRSN_ID,PRSN_TYPE_CD,FAX_PHN_NBR,FRST_NM,MDL_NM,LAST_NM,NM_SFX_CD,MRTL_STAT_CD) values (to_date('19-JAN-16','DD-MON-RR'),'381764634','F','2','N','N',null,null,null,null,6,'B',null,'Angelina','A','Jolie',null,'M');
Insert into ECRS.PRSN (PRSN_DOB_DT,PRSN_SSN,GNDR_CD,ETHNCTY_CD,PRSN_DSBLD_IND,PRSN_STDNT_IND,PRSN_REC_EFCTV_DT,HOME_PHNE_NUM,WORK_PHNE_NUM,ADDR_ID,PRSN_ID,PRSN_TYPE_CD,FAX_PHN_NBR,FRST_NM,MDL_NM,LAST_NM,NM_SFX_CD,MRTL_STAT_CD) values (to_date('19-JAN-16','DD-MON-RR'),'381764634','F','2','N','N',null,null,null,null,7,'B',null,'Angelina','A','Jolie',null,'M');
Insert into ECRS.PRSN (PRSN_DOB_DT,PRSN_SSN,GNDR_CD,ETHNCTY_CD,PRSN_DSBLD_IND,PRSN_STDNT_IND,PRSN_REC_EFCTV_DT,HOME_PHNE_NUM,WORK_PHNE_NUM,ADDR_ID,PRSN_ID,PRSN_TYPE_CD,FAX_PHN_NBR,FRST_NM,MDL_NM,LAST_NM,NM_SFX_CD,MRTL_STAT_CD) values (to_date('19-JAN-16','DD-MON-RR'),'381764634','F','2','N','N',null,null,null,null,8,'B',null,'Angelina','A','Jolie',null,'M');
Insert into ECRS.PRSN (PRSN_DOB_DT,PRSN_SSN,GNDR_CD,ETHNCTY_CD,PRSN_DSBLD_IND,PRSN_STDNT_IND,PRSN_REC_EFCTV_DT,HOME_PHNE_NUM,WORK_PHNE_NUM,ADDR_ID,PRSN_ID,PRSN_TYPE_CD,FAX_PHN_NBR,FRST_NM,MDL_NM,LAST_NM,NM_SFX_CD,MRTL_STAT_CD) values (to_date('19-JAN-16','DD-MON-RR'),'441876231','M','2','N','N',null,null,null,null,2,'B',null,'Deanna','A','Lucero',null,'M');
Insert into ECRS.PRSN (PRSN_DOB_DT,PRSN_SSN,GNDR_CD,ETHNCTY_CD,PRSN_DSBLD_IND,PRSN_STDNT_IND,PRSN_REC_EFCTV_DT,HOME_PHNE_NUM,WORK_PHNE_NUM,ADDR_ID,PRSN_ID,PRSN_TYPE_CD,FAX_PHN_NBR,FRST_NM,MDL_NM,LAST_NM,NM_SFX_CD,MRTL_STAT_CD) values (to_date('19-JAN-16','DD-MON-RR'),'381764634','F','2','N','N',null,null,null,null,3,'B',null,'Angelina','A','Jolie',null,'M');
REM INSERTING into ECRS.PRSN_RACE
SET DEFINE OFF;
Insert into ECRS.PRSN_RACE (PRSN_ID,RACE_CD) values (1,'AS');
Insert into ECRS.PRSN_RACE (PRSN_ID,RACE_CD) values (51,'WH');
REM INSERTING into ECRS.RACE_CD
SET DEFINE OFF;
Insert into ECRS.RACE_CD (RACE_CD,RACE_DESC) values ('WH','WHITE');
Insert into ECRS.RACE_CD (RACE_CD,RACE_DESC) values ('AS','ASIAN');
REM INSERTING into ECRS.RQUST_FOR_DOC
SET DEFINE OFF;
REM INSERTING into ECRS.SFH_PRPRTY
SET DEFINE OFF;
Insert into ECRS.SFH_PRPRTY (SFH_PRPRTY_ID,PRPRTY_ADDR_ID,SFH_UNIT_CNT,PRPRTY_ADDR_VLDTN_IND,CNSTRCTN_CD,BLT_YR,SLR_IND,CNSTRCTN_TO_PRMNNT_IND,MNFCTRNG_HUSNG_IND,PUD_CND_IND,CNSTRCTN_RHB_ESCRW_IND) values (1,1,3,'N','1','1994','N','Y','N','N','N');
REM INSERTING into ECRS.TEST_CASE_RVW
SET DEFINE OFF;
Insert into ECRS.TEST_CASE_RVW (TEST_CASE_RSLT_DESC,TEST_CASE_SQNC_NBR,RVW_CYCL_STRT_DT,LOAN_RVW_ID) values (null,1,to_date('30-JAN-16','DD-MON-RR'),1);
REM INSERTING into ECRS.TRBL_AFL_CD
SET DEFINE OFF;
Insert into ECRS.TRBL_AFL_CD (TRBL_AFL_CD,TRBL_AFL_NAME) values ('CHRK','Cherokee');
Insert into ECRS.TRBL_AFL_CD (TRBL_AFL_CD,TRBL_AFL_NAME) values ('CHKA','Chickasaw');
REM INSERTING into ECRS.UNDRWRTNG_TYPE_CD
SET DEFINE OFF;
Insert into ECRS.UNDRWRTNG_TYPE_CD (UNDRWRTNG_TYPE_CD,UNDRWRTNG_TYPE_CD_DESC) values ('S','Desktop Underwriter (DU)');
Insert into ECRS.UNDRWRTNG_TYPE_CD (UNDRWRTNG_TYPE_CD,UNDRWRTNG_TYPE_CD_DESC) values ('D','Direct Guarantee(DG)');
--------------------------------------------------------
--  DDL for Index CASE_STAT_CD_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."CASE_STAT_CD_PK" ON "ECRS"."CASE_STAT_CD" ("CASE_STAT_CD") 
  ;
--------------------------------------------------------
--  DDL for Index LOAN_RTNG_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."LOAN_RTNG_PK" ON "ECRS"."LOAN_RTNG" ("LOAN_RTNG_ID") 
  ;
--------------------------------------------------------
--  DDL for Index LOAN_RVW_ACTVTY_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."LOAN_RVW_ACTVTY_PK" ON "ECRS"."LOAN_RVW_ACTVTY" ("LOAN_RVW_ACTVTY_ID") 
  ;
--------------------------------------------------------
--  DDL for Index TRBL_AFL_CD_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."TRBL_AFL_CD_PK" ON "ECRS"."TRBL_AFL_CD" ("TRBL_AFL_CD") 
  ;
--------------------------------------------------------
--  DDL for Index XPKACTVTY_RSN_CD
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."XPKACTVTY_RSN_CD" ON "ECRS"."ACTVTY_RSN_CD" ("ACTVTY_RSN_CD") 
  ;
--------------------------------------------------------
--  DDL for Index XPKADP_CD
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."XPKADP_CD" ON "ECRS"."ADP_CD" ("ADP_CD") 
  ;
--------------------------------------------------------
--  DDL for Index XPKAPRSR
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."XPKAPRSR" ON "ECRS"."APRSR" ("APRSR_ID") 
  ;
--------------------------------------------------------
--  DDL for Index XPKBORRWR
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."XPKBORRWR" ON "ECRS"."BORRWR" ("BORRWR_ID") 
  ;
--------------------------------------------------------
--  DDL for Index XPKBorrower_Type_Code
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."XPKBorrower_Type_Code" ON "ECRS"."BORRWR_TYPE_CD" ("BORRWR_TYPE_CD") 
  ;
--------------------------------------------------------
--  DDL for Index XPKCNSTRCTN_CD
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."XPKCNSTRCTN_CD" ON "ECRS"."CNSTRCTN_CD" ("CNSTRCTN_CD") 
  ;
--------------------------------------------------------
--  DDL for Index XPKDFCNCY_LTR
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."XPKDFCNCY_LTR" ON "ECRS"."DFCNCY_LTR" ("DFCNCY_LTR_ID") 
  ;
--------------------------------------------------------
--  DDL for Index XPKDOC
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."XPKDOC" ON "ECRS"."DOC" ("DOC_ID") 
  ;
--------------------------------------------------------
--  DDL for Index XPKDOC_UPLD_RSN_CD
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."XPKDOC_UPLD_RSN_CD" ON "ECRS"."DOC_UPLD_RSN_CD" ("DOC_UPLD_RSN_CD") 
  ;
--------------------------------------------------------
--  DDL for Index XPKETHNCTY_CD
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."XPKETHNCTY_CD" ON "ECRS"."ETHNCTY_CD" ("ETHNCTY_CD") 
  ;
--------------------------------------------------------
--  DDL for Index XPKFHA_LOAN_TYPE_CD
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."XPKFHA_LOAN_TYPE_CD" ON "ECRS"."FHA_LOAN_TYPE_CD" ("FHA_LOAN_TYPE_CD") 
  ;
--------------------------------------------------------
--  DDL for Index XPKGNDR_CD
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."XPKGNDR_CD" ON "ECRS"."GNDR_CD" ("GNDR_CD") 
  ;
--------------------------------------------------------
--  DDL for Index XPKHOC
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."XPKHOC" ON "ECRS"."HOC" ("HOC_ID") 
  ;
--------------------------------------------------------
--  DDL for Index XPKHOC_EMPLYEE_ROLE_CD
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."XPKHOC_EMPLYEE_ROLE_CD" ON "ECRS"."HOC_EMPLYEE_ROLE_CD" ("HOC_EMPLYEE_ROLE_CD") 
  ;
--------------------------------------------------------
--  DDL for Index XPKHOC_LOAN_RVW_PRSNL
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."XPKHOC_LOAN_RVW_PRSNL" ON "ECRS"."HOC_LOAN_RVW_PRSNL" ("LOAN_RVW_PRSNL_ID", "HOC_ID") 
  ;
--------------------------------------------------------
--  DDL for Index XPKHOC_RVW
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."XPKHOC_RVW" ON "ECRS"."HOC_RVW" ("LRS_RVW_LVL_CD", "LOAN_RVW_ID", "LOAN_RVW_PRSNL_ID", "HOC_ID") 
  ;
--------------------------------------------------------
--  DDL for Index XPKHOC_RVWR
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."XPKHOC_RVWR" ON "ECRS"."HOC_RVWR" ("LOAN_RVW_PRSNL_ID", "HOC_ID") 
  ;
--------------------------------------------------------
--  DDL for Index XPKHOC_RVWR_AVLBLTY
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."XPKHOC_RVWR_AVLBLTY" ON "ECRS"."HOC_RVWR_AVLBLTY" ("RVWR_STRT_TEAM_DT", "AVLBL_WEEK_DT", "LOAN_RVW_PRSNL_ID", "HOC_ID") 
  ;
--------------------------------------------------------
--  DDL for Index XPKHOC_RVW_TEAM
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."XPKHOC_RVW_TEAM" ON "ECRS"."HOC_RVW_TEAM" ("RVWR_STRT_TEAM_DT", "LOAN_RVW_PRSNL_ID", "HOC_ID") 
  ;
--------------------------------------------------------
--  DDL for Index XPKHOC_SPRVSR
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."XPKHOC_SPRVSR" ON "ECRS"."HOC_SPRVSR" ("LOAN_RVW_PRSNL_ID", "HOC_ID") 
  ;
--------------------------------------------------------
--  DDL for Index XPKHOC_TEAM_LEAD
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."XPKHOC_TEAM_LEAD" ON "ECRS"."HOC_TEAM_LEAD" ("LOAN_RVW_PRSNL_ID", "HOC_ID") 
  ;
--------------------------------------------------------
--  DDL for Index XPKHUD_RGN_FLD_OFC_CD
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."XPKHUD_RGN_FLD_OFC_CD" ON "ECRS"."HUD_RGN_FLD_OFC_CD" ("HUD_FLD_OFC_CD", "HUD_RGN_CD") 
  ;
--------------------------------------------------------
--  DDL for Index XPKINSPCTR
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."XPKINSPCTR" ON "ECRS"."INSPCTR" ("INSPCTR_ID") 
  ;
--------------------------------------------------------
--  DDL for Index XPKLNDR
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."XPKLNDR" ON "ECRS"."LNDR" ("LNDR_ID") 
  ;
--------------------------------------------------------
--  DDL for Index XPKLNDR_RESP_LTR
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."XPKLNDR_RESP_LTR" ON "ECRS"."LNDR_RESP_LTR" ("LNDR_RESP_LTR_ID") 
  ;
--------------------------------------------------------
--  DDL for Index XPKLOAN
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."XPKLOAN" ON "ECRS"."LOAN" ("LOAN_ID") 
  ;
--------------------------------------------------------
--  DDL for Index XPKLOAN_BORRWR
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."XPKLOAN_BORRWR" ON "ECRS"."LOAN_BORRWR" ("BORRWR_ID", "LOAN_ID") 
  ;
--------------------------------------------------------
--  DDL for Index XPKLOAN_FNDS_TO_CLS
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."XPKLOAN_FNDS_TO_CLS" ON "ECRS"."LOAN_FNDS_TO_CLS" ("LOAN_ID") 
  ;
--------------------------------------------------------
--  DDL for Index XPKLOAN_MORTG
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."XPKLOAN_MORTG" ON "ECRS"."LOAN_MORTG" ("LOAN_ID") 
  ;
--------------------------------------------------------
--  DDL for Index XPKLOAN_OFCR
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."XPKLOAN_OFCR" ON "ECRS"."LOAN_OFCR" ("LOAN_OFCR_ID") 
  ;
--------------------------------------------------------
--  DDL for Index XPKLOAN_ORGNTR
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."XPKLOAN_ORGNTR" ON "ECRS"."LOAN_ORGNTR" ("LOAN_ORGNTR_ID") 
  ;
--------------------------------------------------------
--  DDL for Index XPKLOAN_PRPS_CD
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."XPKLOAN_PRPS_CD" ON "ECRS"."LOAN_PRPS_CD" ("LOAN_PRPS_CD") 
  ;
--------------------------------------------------------
--  DDL for Index XPKLOAN_RVW
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."XPKLOAN_RVW" ON "ECRS"."LOAN_RVW" ("LOAN_RVW_ID") 
  ;
--------------------------------------------------------
--  DDL for Index XPKLOAN_RVW_PRSNL
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."XPKLOAN_RVW_PRSNL" ON "ECRS"."LOAN_RVW_PRSNL" ("LOAN_RVW_PRSNL_ID") 
  ;
--------------------------------------------------------
--  DDL for Index XPKLOAN_RVW_SKL_MTRX
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."XPKLOAN_RVW_SKL_MTRX" ON "ECRS"."LOAN_RVW_SKL_MTRX" ("FHA_LOAN_TYPE_CD", "LOAN_RVW_PRSNL_ID") 
  ;
--------------------------------------------------------
--  DDL for Index XPKLRS_DFCNCY
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."XPKLRS_DFCNCY" ON "ECRS"."LRS_DFCNCY" ("LOAN_RVW_ID") 
  ;
--------------------------------------------------------
--  DDL for Index XPKLRS_DFCNCY_CTGRY_CD
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."XPKLRS_DFCNCY_CTGRY_CD" ON "ECRS"."LRS_DFCNCY_CTGRY_CD" ("LRS_DFCNCY_CTGRY_CD") 
  ;
--------------------------------------------------------
--  DDL for Index XPKLRS_DFCNCY_CTGRY_TYPE
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."XPKLRS_DFCNCY_CTGRY_TYPE" ON "ECRS"."LRS_DFCNCY_CTGRY_TYPE" ("LRS_DFCNCY_CTGRY_CD", "LRS_DFCNCY_TYPE_CD") 
  ;
--------------------------------------------------------
--  DDL for Index XPKLRS_DFCNCY_LTR
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."XPKLRS_DFCNCY_LTR" ON "ECRS"."LRS_DFCNCY_LTR" ("DFCNCY_LTR_ID", "LOAN_RVW_ID") 
  ;
--------------------------------------------------------
--  DDL for Index XPKLRS_DFCNCY_TYPE_CD
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."XPKLRS_DFCNCY_TYPE_CD" ON "ECRS"."LRS_DFCNCY_TYPE_CD" ("LRS_DFCNCY_TYPE_CD") 
  ;
--------------------------------------------------------
--  DDL for Index XPKLRS_RVW_LVL_CD
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."XPKLRS_RVW_LVL_CD" ON "ECRS"."LRS_RVW_LVL_CD" ("LRS_RVW_LVL_CD") 
  ;
--------------------------------------------------------
--  DDL for Index XPKLRS_RVW_TYPE_CD
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."XPKLRS_RVW_TYPE_CD" ON "ECRS"."LRS_RVW_TYPE_CD" ("LRS_RVW_TYPE_CD") 
  ;
--------------------------------------------------------
--  DDL for Index XPKLRS_STAT_CD
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."XPKLRS_STAT_CD" ON "ECRS"."LRS_STAT_CD" ("LRS_STAT_CD") 
  ;
--------------------------------------------------------
--  DDL for Index XPKMRTL_STAT_CD
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."XPKMRTL_STAT_CD" ON "ECRS"."MRTL_STAT_CD" ("MRTL_STAT_CD") 
  ;
--------------------------------------------------------
--  DDL for Index XPKPOST_ENDRSMNT_TECH_RVW
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."XPKPOST_ENDRSMNT_TECH_RVW" ON "ECRS"."POST_ENDRSMNT_TECH_RVW" ("LOAN_RVW_ID") 
  ;
--------------------------------------------------------
--  DDL for Index XPKPRSN
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."XPKPRSN" ON "ECRS"."PRSN" ("PRSN_ID") 
  ;
--------------------------------------------------------
--  DDL for Index XPKPRSN_RACE
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."XPKPRSN_RACE" ON "ECRS"."PRSN_RACE" ("PRSN_ID", "RACE_CD") 
  ;
--------------------------------------------------------
--  DDL for Index XPKRACE_CD
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."XPKRACE_CD" ON "ECRS"."RACE_CD" ("RACE_CD") 
  ;
--------------------------------------------------------
--  DDL for Index XPKRQUST_FOR_DOC
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."XPKRQUST_FOR_DOC" ON "ECRS"."RQUST_FOR_DOC" ("RQUST_FOR_DOC_ID") 
  ;
--------------------------------------------------------
--  DDL for Index XPKSFH_PRPRTY
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."XPKSFH_PRPRTY" ON "ECRS"."SFH_PRPRTY" ("SFH_PRPRTY_ID") 
  ;
--------------------------------------------------------
--  DDL for Index XPKTEST_CASE_RVW
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."XPKTEST_CASE_RVW" ON "ECRS"."TEST_CASE_RVW" ("TEST_CASE_SQNC_NBR", "LOAN_RVW_ID") 
  ;
--------------------------------------------------------
--  DDL for Index XPKUNDRWRTNG_TYPE_CD
--------------------------------------------------------

  CREATE UNIQUE INDEX "ECRS"."XPKUNDRWRTNG_TYPE_CD" ON "ECRS"."UNDRWRTNG_TYPE_CD" ("UNDRWRTNG_TYPE_CD") 
  ;
--------------------------------------------------------
--  Constraints for Table ACTVTY_RSN_CD
--------------------------------------------------------

  ALTER TABLE "ECRS"."ACTVTY_RSN_CD" ADD CONSTRAINT "XPKACTVTY_RSN_CD" PRIMARY KEY ("ACTVTY_RSN_CD")
  USING INDEX  ENABLE;
  ALTER TABLE "ECRS"."ACTVTY_RSN_CD" MODIFY ("ACTVTY_RSN_CD" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ADP_CD
--------------------------------------------------------

  ALTER TABLE "ECRS"."ADP_CD" ADD CONSTRAINT "XPKADP_CD" PRIMARY KEY ("ADP_CD")
  USING INDEX  ENABLE;
  ALTER TABLE "ECRS"."ADP_CD" MODIFY ("ADP_CD" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table APRSR
--------------------------------------------------------

  ALTER TABLE "ECRS"."APRSR" ADD CONSTRAINT "XPKAPRSR" PRIMARY KEY ("APRSR_ID")
  USING INDEX  ENABLE;
  ALTER TABLE "ECRS"."APRSR" MODIFY ("APRSR_ID" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."APRSR" MODIFY ("APRSR_203K_CRTFD_IND" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."APRSR" MODIFY ("APRSR_AVLBL_IND" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."APRSR" MODIFY ("APRSR_FLD_RVW_AUTH_IND" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."APRSR" MODIFY ("ST_LCNCD_IND" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."APRSR" MODIFY ("STD_APRSL_CRTFD_IND" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table BORRWR
--------------------------------------------------------

  ALTER TABLE "ECRS"."BORRWR" ADD CONSTRAINT "XPKBORRWR" PRIMARY KEY ("BORRWR_ID")
  USING INDEX  ENABLE NOVALIDATE;
  ALTER TABLE "ECRS"."BORRWR" MODIFY ("FRST_TIME_HOME_BYR_IND" NOT NULL ENABLE NOVALIDATE);
  ALTER TABLE "ECRS"."BORRWR" MODIFY ("RNTNG_IND" NOT NULL ENABLE NOVALIDATE);
  ALTER TABLE "ECRS"."BORRWR" MODIFY ("SELF_EMPLYD_IND" NOT NULL ENABLE NOVALIDATE);
  ALTER TABLE "ECRS"."BORRWR" MODIFY ("BORRWR_ID" NOT NULL ENABLE NOVALIDATE);
  ALTER TABLE "ECRS"."BORRWR" MODIFY ("TRBL_AFL_CD" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."BORRWR" MODIFY ("BORRWR_TYPE_CD" NOT NULL ENABLE NOVALIDATE);
--------------------------------------------------------
--  Constraints for Table BORRWR_TYPE_CD
--------------------------------------------------------

  ALTER TABLE "ECRS"."BORRWR_TYPE_CD" ADD CONSTRAINT "XPKBorrower_Type_Code" PRIMARY KEY ("BORRWR_TYPE_CD")
  USING INDEX  ENABLE;
  ALTER TABLE "ECRS"."BORRWR_TYPE_CD" MODIFY ("BORRWR_TYPE_CD" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table CASE_STAT_CD
--------------------------------------------------------

  ALTER TABLE "ECRS"."CASE_STAT_CD" ADD CONSTRAINT "CASE_STAT_CD_PK" PRIMARY KEY ("CASE_STAT_CD")
  USING INDEX  ENABLE;
  ALTER TABLE "ECRS"."CASE_STAT_CD" MODIFY ("CASE_STAT_CD_DESC" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."CASE_STAT_CD" MODIFY ("CASE_STAT_CD" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table CNSTRCTN_CD
--------------------------------------------------------

  ALTER TABLE "ECRS"."CNSTRCTN_CD" ADD CONSTRAINT "XPKCNSTRCTN_CD" PRIMARY KEY ("CNSTRCTN_CD")
  USING INDEX  ENABLE;
  ALTER TABLE "ECRS"."CNSTRCTN_CD" MODIFY ("CNSTRCTN_CD" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table DFCNCY_LTR
--------------------------------------------------------

  ALTER TABLE "ECRS"."DFCNCY_LTR" ADD CONSTRAINT "XPKDFCNCY_LTR" PRIMARY KEY ("DFCNCY_LTR_ID")
  USING INDEX  ENABLE;
  ALTER TABLE "ECRS"."DFCNCY_LTR" MODIFY ("LOAN_RVW_ID" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."DFCNCY_LTR" MODIFY ("DFCNCY_LTR_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table DOC
--------------------------------------------------------

  ALTER TABLE "ECRS"."DOC" ADD CONSTRAINT "XPKDOC" PRIMARY KEY ("DOC_ID")
  USING INDEX  ENABLE;
  ALTER TABLE "ECRS"."DOC" MODIFY ("SEND_MSGE_TO_RVW_IND" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."DOC" MODIFY ("DOC_UPLD_RSN_CD" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."DOC" MODIFY ("DOC_TYPE_CD" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."DOC" MODIFY ("DOC_CMPLTNS_IND" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."DOC" MODIFY ("DOC_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table DOC_UPLD_RSN_CD
--------------------------------------------------------

  ALTER TABLE "ECRS"."DOC_UPLD_RSN_CD" ADD CONSTRAINT "XPKDOC_UPLD_RSN_CD" PRIMARY KEY ("DOC_UPLD_RSN_CD")
  USING INDEX  ENABLE;
  ALTER TABLE "ECRS"."DOC_UPLD_RSN_CD" MODIFY ("DOC_UPLD_RSN_CD" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ETHNCTY_CD
--------------------------------------------------------

  ALTER TABLE "ECRS"."ETHNCTY_CD" ADD CONSTRAINT "XPKETHNCTY_CD" PRIMARY KEY ("ETHNCTY_CD")
  USING INDEX  ENABLE;
  ALTER TABLE "ECRS"."ETHNCTY_CD" MODIFY ("ETHNCTY_CD" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table FHA_LOAN_TYPE_CD
--------------------------------------------------------

  ALTER TABLE "ECRS"."FHA_LOAN_TYPE_CD" ADD CONSTRAINT "XPKFHA_LOAN_TYPE_CD" PRIMARY KEY ("FHA_LOAN_TYPE_CD")
  USING INDEX  ENABLE;
  ALTER TABLE "ECRS"."FHA_LOAN_TYPE_CD" MODIFY ("FHA_LOAN_TYPE_CD" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table GNDR_CD
--------------------------------------------------------

  ALTER TABLE "ECRS"."GNDR_CD" ADD CONSTRAINT "XPKGNDR_CD" PRIMARY KEY ("GNDR_CD")
  USING INDEX  ENABLE;
  ALTER TABLE "ECRS"."GNDR_CD" MODIFY ("GNDR_CD" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table HOC
--------------------------------------------------------

  ALTER TABLE "ECRS"."HOC" ADD CONSTRAINT "XPKHOC" PRIMARY KEY ("HOC_ID")
  USING INDEX  ENABLE;
  ALTER TABLE "ECRS"."HOC" MODIFY ("HOC_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table HOC_EMPLYEE_ROLE_CD
--------------------------------------------------------

  ALTER TABLE "ECRS"."HOC_EMPLYEE_ROLE_CD" ADD CONSTRAINT "XPKHOC_EMPLYEE_ROLE_CD" PRIMARY KEY ("HOC_EMPLYEE_ROLE_CD")
  USING INDEX  ENABLE;
  ALTER TABLE "ECRS"."HOC_EMPLYEE_ROLE_CD" MODIFY ("HOC_EMPLYEE_ROLE_CD" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table HOC_LOAN_RVW_PRSNL
--------------------------------------------------------

  ALTER TABLE "ECRS"."HOC_LOAN_RVW_PRSNL" ADD CONSTRAINT "XPKHOC_LOAN_RVW_PRSNL" PRIMARY KEY ("LOAN_RVW_PRSNL_ID", "HOC_ID")
  USING INDEX  ENABLE;
  ALTER TABLE "ECRS"."HOC_LOAN_RVW_PRSNL" MODIFY ("HOC_ID" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."HOC_LOAN_RVW_PRSNL" MODIFY ("LOAN_RVW_PRSNL_ID" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."HOC_LOAN_RVW_PRSNL" MODIFY ("HOC_EMPLYEE_ROLE_CD" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table HOC_RVW
--------------------------------------------------------

  ALTER TABLE "ECRS"."HOC_RVW" ADD CONSTRAINT "XPKHOC_RVW" PRIMARY KEY ("LRS_RVW_LVL_CD", "LOAN_RVW_ID", "LOAN_RVW_PRSNL_ID", "HOC_ID")
  USING INDEX  ENABLE;
  ALTER TABLE "ECRS"."HOC_RVW" MODIFY ("HOC_ID" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."HOC_RVW" MODIFY ("LOAN_RVW_PRSNL_ID" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."HOC_RVW" MODIFY ("LOAN_RVW_ID" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."HOC_RVW" MODIFY ("LRS_RVW_LVL_CD" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table HOC_RVWR
--------------------------------------------------------

  ALTER TABLE "ECRS"."HOC_RVWR" ADD CONSTRAINT "XPKHOC_RVWR" PRIMARY KEY ("LOAN_RVW_PRSNL_ID", "HOC_ID")
  USING INDEX  ENABLE;
  ALTER TABLE "ECRS"."HOC_RVWR" MODIFY ("HOC_ID" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."HOC_RVWR" MODIFY ("LOAN_RVW_PRSNL_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table HOC_RVWR_AVLBLTY
--------------------------------------------------------

  ALTER TABLE "ECRS"."HOC_RVWR_AVLBLTY" ADD CONSTRAINT "XPKHOC_RVWR_AVLBLTY" PRIMARY KEY ("RVWR_STRT_TEAM_DT", "AVLBL_WEEK_DT", "LOAN_RVW_PRSNL_ID", "HOC_ID")
  USING INDEX  ENABLE;
  ALTER TABLE "ECRS"."HOC_RVWR_AVLBLTY" MODIFY ("HOC_ID" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."HOC_RVWR_AVLBLTY" MODIFY ("LOAN_RVW_PRSNL_ID" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."HOC_RVWR_AVLBLTY" MODIFY ("AVLBL_WEEK_DT" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."HOC_RVWR_AVLBLTY" MODIFY ("RVWR_STRT_TEAM_DT" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table HOC_RVW_TEAM
--------------------------------------------------------

  ALTER TABLE "ECRS"."HOC_RVW_TEAM" ADD CONSTRAINT "XPKHOC_RVW_TEAM" PRIMARY KEY ("RVWR_STRT_TEAM_DT", "LOAN_RVW_PRSNL_ID", "HOC_ID")
  USING INDEX  ENABLE;
  ALTER TABLE "ECRS"."HOC_RVW_TEAM" MODIFY ("HOC_ID" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."HOC_RVW_TEAM" MODIFY ("LOAN_RVW_PRSNL_ID" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."HOC_RVW_TEAM" MODIFY ("RVWR_STRT_TEAM_DT" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table HOC_SPRVSR
--------------------------------------------------------

  ALTER TABLE "ECRS"."HOC_SPRVSR" ADD CONSTRAINT "XPKHOC_SPRVSR" PRIMARY KEY ("LOAN_RVW_PRSNL_ID", "HOC_ID")
  USING INDEX  ENABLE;
  ALTER TABLE "ECRS"."HOC_SPRVSR" MODIFY ("HOC_ID" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."HOC_SPRVSR" MODIFY ("LOAN_RVW_PRSNL_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table HOC_TEAM_LEAD
--------------------------------------------------------

  ALTER TABLE "ECRS"."HOC_TEAM_LEAD" ADD CONSTRAINT "XPKHOC_TEAM_LEAD" PRIMARY KEY ("LOAN_RVW_PRSNL_ID", "HOC_ID")
  USING INDEX  ENABLE;
  ALTER TABLE "ECRS"."HOC_TEAM_LEAD" MODIFY ("HOC_ID" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."HOC_TEAM_LEAD" MODIFY ("LOAN_RVW_PRSNL_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table HUD_RGN_FLD_OFC_CD
--------------------------------------------------------

  ALTER TABLE "ECRS"."HUD_RGN_FLD_OFC_CD" ADD CONSTRAINT "XPKHUD_RGN_FLD_OFC_CD" PRIMARY KEY ("HUD_FLD_OFC_CD", "HUD_RGN_CD")
  USING INDEX  ENABLE;
  ALTER TABLE "ECRS"."HUD_RGN_FLD_OFC_CD" MODIFY ("HUD_FLD_OFC_ST_CD" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."HUD_RGN_FLD_OFC_CD" MODIFY ("HUD_RGN_CD" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."HUD_RGN_FLD_OFC_CD" MODIFY ("HUD_FLD_OFC_CD" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table INSPCTR
--------------------------------------------------------

  ALTER TABLE "ECRS"."INSPCTR" ADD CONSTRAINT "XPKINSPCTR" PRIMARY KEY ("INSPCTR_ID")
  USING INDEX  ENABLE;
  ALTER TABLE "ECRS"."INSPCTR" MODIFY ("INSPCTR_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table LNDR
--------------------------------------------------------

  ALTER TABLE "ECRS"."LNDR" ADD CONSTRAINT "XPKLNDR" PRIMARY KEY ("LNDR_ID")
  USING INDEX  ENABLE;
  ALTER TABLE "ECRS"."LNDR" MODIFY ("LNDR_ID" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."LNDR" MODIFY ("LNDR_CTGRY_CD" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."LNDR" MODIFY ("DRCT_ENDRSMNT_PRCSNG_IND" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."LNDR" MODIFY ("HECM_CRTFD_IND" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."LNDR" MODIFY ("HECM_PRE_CLSNG_IND" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."LNDR" MODIFY ("HIGH_RISK_IND" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."LNDR" MODIFY ("LMTD_DNL_PRTCPTN_IND" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."LNDR" MODIFY ("PRE_CLSNG_PRCSNG_IND" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table LNDR_RESP_LTR
--------------------------------------------------------

  ALTER TABLE "ECRS"."LNDR_RESP_LTR" ADD CONSTRAINT "XPKLNDR_RESP_LTR" PRIMARY KEY ("LNDR_RESP_LTR_ID")
  USING INDEX  ENABLE;
  ALTER TABLE "ECRS"."LNDR_RESP_LTR" MODIFY ("DFCNCY_MTGTN_CMPLT_IND" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."LNDR_RESP_LTR" MODIFY ("LNDR_RESP_LTR_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table LOAN
--------------------------------------------------------

  ALTER TABLE "ECRS"."LOAN" ADD CONSTRAINT "XPKLOAN" PRIMARY KEY ("LOAN_ID")
  USING INDEX  ENABLE;
  ALTER TABLE "ECRS"."LOAN" MODIFY ("LOAN_ID" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."LOAN" MODIFY ("UNDRWRTNG_TYPE_CD" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."LOAN" MODIFY ("CASE_STAT_CD" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."LOAN" MODIFY ("LOAN_PRPS_CD" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."LOAN" MODIFY ("LOAN_ORGNTR_ID" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."LOAN" MODIFY ("LOAN_OFCR_ID" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."LOAN" MODIFY ("ADP_CD" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."LOAN" MODIFY ("BORRWR_ID" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."LOAN" MODIFY ("LOAN_APPLCTN_CRTFCTN_IND" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."LOAN" MODIFY ("HUD_FLD_OFC_CD" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."LOAN" MODIFY ("HUD_RGN_CD" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."LOAN" MODIFY ("APRSR_ID" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."LOAN" MODIFY ("INSPCTR_ID" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."LOAN" MODIFY ("FHA_LOAN_TYPE_CD" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."LOAN" MODIFY ("HOC_ID" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."LOAN" MODIFY ("SFH_PRPRTY_ID" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."LOAN" MODIFY ("LNDR_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table LOAN_BORRWR
--------------------------------------------------------

  ALTER TABLE "ECRS"."LOAN_BORRWR" ADD CONSTRAINT "XPKLOAN_BORRWR" PRIMARY KEY ("BORRWR_ID", "LOAN_ID")
  USING INDEX  ENABLE;
  ALTER TABLE "ECRS"."LOAN_BORRWR" MODIFY ("LOAN_ID" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."LOAN_BORRWR" MODIFY ("BORRWR_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table LOAN_FNDS_TO_CLS
--------------------------------------------------------

  ALTER TABLE "ECRS"."LOAN_FNDS_TO_CLS" ADD CONSTRAINT "XPKLOAN_FNDS_TO_CLS" PRIMARY KEY ("LOAN_ID")
  USING INDEX  ENABLE;
  ALTER TABLE "ECRS"."LOAN_FNDS_TO_CLS" MODIFY ("LOAN_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table LOAN_MORTG
--------------------------------------------------------

  ALTER TABLE "ECRS"."LOAN_MORTG" ADD CONSTRAINT "XPKLOAN_MORTG" PRIMARY KEY ("LOAN_ID")
  USING INDEX  ENABLE;
  ALTER TABLE "ECRS"."LOAN_MORTG" MODIFY ("LOAN_ID" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."LOAN_MORTG" MODIFY ("LG_FEE_FNCL_IND" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."LOAN_MORTG" MODIFY ("PYMNTS_CURR_IND" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table LOAN_OFCR
--------------------------------------------------------

  ALTER TABLE "ECRS"."LOAN_OFCR" ADD CONSTRAINT "XPKLOAN_OFCR" PRIMARY KEY ("LOAN_OFCR_ID")
  USING INDEX  ENABLE;
  ALTER TABLE "ECRS"."LOAN_OFCR" MODIFY ("LNDR_ID" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."LOAN_OFCR" MODIFY ("LOAN_OFCR_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table LOAN_ORGNTR
--------------------------------------------------------

  ALTER TABLE "ECRS"."LOAN_ORGNTR" ADD CONSTRAINT "XPKLOAN_ORGNTR" PRIMARY KEY ("LOAN_ORGNTR_ID")
  USING INDEX  ENABLE;
  ALTER TABLE "ECRS"."LOAN_ORGNTR" MODIFY ("LOAN_ORGNTR_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table LOAN_PRPS_CD
--------------------------------------------------------

  ALTER TABLE "ECRS"."LOAN_PRPS_CD" ADD CONSTRAINT "XPKLOAN_PRPS_CD" PRIMARY KEY ("LOAN_PRPS_CD")
  USING INDEX  ENABLE;
  ALTER TABLE "ECRS"."LOAN_PRPS_CD" MODIFY ("LOAN_PRPS_CD" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table LOAN_RTNG
--------------------------------------------------------

  ALTER TABLE "ECRS"."LOAN_RTNG" MODIFY ("LOAN_RVW_ID" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."LOAN_RTNG" ADD CONSTRAINT "LOAN_RTNG_PK" PRIMARY KEY ("LOAN_RTNG_ID")
  USING INDEX  ENABLE;
  ALTER TABLE "ECRS"."LOAN_RTNG" MODIFY ("LOAN_RTNG_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table LOAN_RVW
--------------------------------------------------------

  ALTER TABLE "ECRS"."LOAN_RVW" ADD CONSTRAINT "XPKLOAN_RVW" PRIMARY KEY ("LOAN_RVW_ID")
  USING INDEX  ENABLE;
  ALTER TABLE "ECRS"."LOAN_RVW" MODIFY ("LOAN_ID" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."LOAN_RVW" MODIFY ("LOAN_RVW_ID" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."LOAN_RVW" MODIFY ("LRS_STAT_CD" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."LOAN_RVW" MODIFY ("SLCTN_SRC_CD" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."LOAN_RVW" MODIFY ("LRS_RVW_TYPE_CD" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table LOAN_RVW_ACTVTY
--------------------------------------------------------

  ALTER TABLE "ECRS"."LOAN_RVW_ACTVTY" ADD CONSTRAINT "LOAN_RVW_ACTVTY_PK" PRIMARY KEY ("LOAN_RVW_ACTVTY_ID")
  USING INDEX  ENABLE;
  ALTER TABLE "ECRS"."LOAN_RVW_ACTVTY" MODIFY ("DOC_ID" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."LOAN_RVW_ACTVTY" MODIFY ("ACTVTY_STRT_DT" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table LOAN_RVW_PRSNL
--------------------------------------------------------

  ALTER TABLE "ECRS"."LOAN_RVW_PRSNL" ADD CONSTRAINT "XPKLOAN_RVW_PRSNL" PRIMARY KEY ("LOAN_RVW_PRSNL_ID")
  USING INDEX  ENABLE;
  ALTER TABLE "ECRS"."LOAN_RVW_PRSNL" MODIFY ("LOAN_RVW_PRSNL_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table LOAN_RVW_SKL_MTRX
--------------------------------------------------------

  ALTER TABLE "ECRS"."LOAN_RVW_SKL_MTRX" ADD CONSTRAINT "XPKLOAN_RVW_SKL_MTRX" PRIMARY KEY ("FHA_LOAN_TYPE_CD", "LOAN_RVW_PRSNL_ID")
  USING INDEX  ENABLE;
  ALTER TABLE "ECRS"."LOAN_RVW_SKL_MTRX" MODIFY ("LOAN_RVW_PRSNL_ID" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."LOAN_RVW_SKL_MTRX" MODIFY ("FHA_LOAN_TYPE_CD" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table LRS_DFCNCY
--------------------------------------------------------

  ALTER TABLE "ECRS"."LRS_DFCNCY" ADD CONSTRAINT "XPKLRS_DFCNCY" PRIMARY KEY ("LOAN_RVW_ID")
  USING INDEX  ENABLE;
  ALTER TABLE "ECRS"."LRS_DFCNCY" MODIFY ("LOAN_RVW_ID" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."LRS_DFCNCY" MODIFY ("LRS_DFCNCY_TYPE_CD" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."LRS_DFCNCY" MODIFY ("LRS_DFCNCY_CTGRY_CD" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table LRS_DFCNCY_CTGRY_CD
--------------------------------------------------------

  ALTER TABLE "ECRS"."LRS_DFCNCY_CTGRY_CD" ADD CONSTRAINT "XPKLRS_DFCNCY_CTGRY_CD" PRIMARY KEY ("LRS_DFCNCY_CTGRY_CD")
  USING INDEX  ENABLE;
  ALTER TABLE "ECRS"."LRS_DFCNCY_CTGRY_CD" MODIFY ("LRS_DFCNCY_CTGRY_CD" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table LRS_DFCNCY_CTGRY_TYPE
--------------------------------------------------------

  ALTER TABLE "ECRS"."LRS_DFCNCY_CTGRY_TYPE" ADD CONSTRAINT "XPKLRS_DFCNCY_CTGRY_TYPE" PRIMARY KEY ("LRS_DFCNCY_CTGRY_CD", "LRS_DFCNCY_TYPE_CD")
  USING INDEX  ENABLE;
  ALTER TABLE "ECRS"."LRS_DFCNCY_CTGRY_TYPE" MODIFY ("LRS_DFCNCY_TYPE_CD" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."LRS_DFCNCY_CTGRY_TYPE" MODIFY ("LRS_DFCNCY_CTGRY_CD" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table LRS_DFCNCY_LTR
--------------------------------------------------------

  ALTER TABLE "ECRS"."LRS_DFCNCY_LTR" ADD CONSTRAINT "XPKLRS_DFCNCY_LTR" PRIMARY KEY ("DFCNCY_LTR_ID", "LOAN_RVW_ID")
  USING INDEX  ENABLE;
  ALTER TABLE "ECRS"."LRS_DFCNCY_LTR" MODIFY ("LOAN_RVW_ID" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."LRS_DFCNCY_LTR" MODIFY ("LNDR_RESP_LTR_ID" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."LRS_DFCNCY_LTR" MODIFY ("DFCNCY_LTR_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table LRS_DFCNCY_TYPE_CD
--------------------------------------------------------

  ALTER TABLE "ECRS"."LRS_DFCNCY_TYPE_CD" ADD CONSTRAINT "XPKLRS_DFCNCY_TYPE_CD" PRIMARY KEY ("LRS_DFCNCY_TYPE_CD")
  USING INDEX  ENABLE;
  ALTER TABLE "ECRS"."LRS_DFCNCY_TYPE_CD" MODIFY ("LRS_DFCNCY_TYPE_CD" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table LRS_RVW_LVL_CD
--------------------------------------------------------

  ALTER TABLE "ECRS"."LRS_RVW_LVL_CD" ADD CONSTRAINT "XPKLRS_RVW_LVL_CD" PRIMARY KEY ("LRS_RVW_LVL_CD")
  USING INDEX  ENABLE;
  ALTER TABLE "ECRS"."LRS_RVW_LVL_CD" MODIFY ("LRS_RVW_LVL_CD" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table LRS_RVW_TYPE_CD
--------------------------------------------------------

  ALTER TABLE "ECRS"."LRS_RVW_TYPE_CD" ADD CONSTRAINT "XPKLRS_RVW_TYPE_CD" PRIMARY KEY ("LRS_RVW_TYPE_CD")
  USING INDEX  ENABLE;
  ALTER TABLE "ECRS"."LRS_RVW_TYPE_CD" MODIFY ("LRS_RVW_TYPE_CD" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table LRS_STAT_CD
--------------------------------------------------------

  ALTER TABLE "ECRS"."LRS_STAT_CD" ADD CONSTRAINT "XPKLRS_STAT_CD" PRIMARY KEY ("LRS_STAT_CD")
  USING INDEX  ENABLE;
  ALTER TABLE "ECRS"."LRS_STAT_CD" MODIFY ("LRS_STAT_CD" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table MRTL_STAT_CD
--------------------------------------------------------

  ALTER TABLE "ECRS"."MRTL_STAT_CD" ADD CONSTRAINT "XPKMRTL_STAT_CD" PRIMARY KEY ("MRTL_STAT_CD")
  USING INDEX  ENABLE;
  ALTER TABLE "ECRS"."MRTL_STAT_CD" MODIFY ("MRTL_STAT_CD" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table POST_ENDRSMNT_TECH_RVW
--------------------------------------------------------

  ALTER TABLE "ECRS"."POST_ENDRSMNT_TECH_RVW" ADD CONSTRAINT "XPKPOST_ENDRSMNT_TECH_RVW" PRIMARY KEY ("LOAN_RVW_ID")
  USING INDEX  ENABLE;
  ALTER TABLE "ECRS"."POST_ENDRSMNT_TECH_RVW" MODIFY ("LOAN_RVW_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table PRSN
--------------------------------------------------------

  ALTER TABLE "ECRS"."PRSN" ADD CONSTRAINT "XPKPRSN" PRIMARY KEY ("PRSN_ID")
  USING INDEX  ENABLE;
  ALTER TABLE "ECRS"."PRSN" MODIFY ("MRTL_STAT_CD" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."PRSN" MODIFY ("PRSN_TYPE_CD" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."PRSN" MODIFY ("PRSN_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table PRSN_RACE
--------------------------------------------------------

  ALTER TABLE "ECRS"."PRSN_RACE" ADD CONSTRAINT "XPKPRSN_RACE" PRIMARY KEY ("PRSN_ID", "RACE_CD")
  USING INDEX  ENABLE;
  ALTER TABLE "ECRS"."PRSN_RACE" MODIFY ("RACE_CD" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."PRSN_RACE" MODIFY ("PRSN_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table RACE_CD
--------------------------------------------------------

  ALTER TABLE "ECRS"."RACE_CD" ADD CONSTRAINT "XPKRACE_CD" PRIMARY KEY ("RACE_CD")
  USING INDEX  ENABLE;
  ALTER TABLE "ECRS"."RACE_CD" MODIFY ("RACE_CD" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table RQUST_FOR_DOC
--------------------------------------------------------

  ALTER TABLE "ECRS"."RQUST_FOR_DOC" ADD CONSTRAINT "XPKRQUST_FOR_DOC" PRIMARY KEY ("RQUST_FOR_DOC_ID")
  USING INDEX  ENABLE;
  ALTER TABLE "ECRS"."RQUST_FOR_DOC" MODIFY ("LOAN_RVW_ID" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."RQUST_FOR_DOC" MODIFY ("RQUST_FOR_DOC_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table SFH_PRPRTY
--------------------------------------------------------

  ALTER TABLE "ECRS"."SFH_PRPRTY" ADD CONSTRAINT "XPKSFH_PRPRTY" PRIMARY KEY ("SFH_PRPRTY_ID")
  USING INDEX  ENABLE;
  ALTER TABLE "ECRS"."SFH_PRPRTY" MODIFY ("CNSTRCTN_RHB_ESCRW_IND" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."SFH_PRPRTY" MODIFY ("PUD_CND_IND" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."SFH_PRPRTY" MODIFY ("MNFCTRNG_HUSNG_IND" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."SFH_PRPRTY" MODIFY ("CNSTRCTN_TO_PRMNNT_IND" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."SFH_PRPRTY" MODIFY ("SLR_IND" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."SFH_PRPRTY" MODIFY ("CNSTRCTN_CD" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."SFH_PRPRTY" MODIFY ("PRPRTY_ADDR_VLDTN_IND" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."SFH_PRPRTY" MODIFY ("PRPRTY_ADDR_ID" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."SFH_PRPRTY" MODIFY ("SFH_PRPRTY_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table TEST_CASE_RVW
--------------------------------------------------------

  ALTER TABLE "ECRS"."TEST_CASE_RVW" ADD CONSTRAINT "XPKTEST_CASE_RVW" PRIMARY KEY ("TEST_CASE_SQNC_NBR", "LOAN_RVW_ID")
  USING INDEX  ENABLE;
  ALTER TABLE "ECRS"."TEST_CASE_RVW" MODIFY ("LOAN_RVW_ID" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."TEST_CASE_RVW" MODIFY ("TEST_CASE_SQNC_NBR" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table TRBL_AFL_CD
--------------------------------------------------------

  ALTER TABLE "ECRS"."TRBL_AFL_CD" ADD CONSTRAINT "TRBL_AFL_CD_PK" PRIMARY KEY ("TRBL_AFL_CD")
  USING INDEX  ENABLE;
  ALTER TABLE "ECRS"."TRBL_AFL_CD" MODIFY ("TRBL_AFL_NAME" NOT NULL ENABLE);
  ALTER TABLE "ECRS"."TRBL_AFL_CD" MODIFY ("TRBL_AFL_CD" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table UNDRWRTNG_TYPE_CD
--------------------------------------------------------

  ALTER TABLE "ECRS"."UNDRWRTNG_TYPE_CD" ADD CONSTRAINT "XPKUNDRWRTNG_TYPE_CD" PRIMARY KEY ("UNDRWRTNG_TYPE_CD")
  USING INDEX  ENABLE;
  ALTER TABLE "ECRS"."UNDRWRTNG_TYPE_CD" MODIFY ("UNDRWRTNG_TYPE_CD" NOT NULL ENABLE);
--------------------------------------------------------
--  Ref Constraints for Table BORRWR
--------------------------------------------------------

  ALTER TABLE "ECRS"."BORRWR" ADD CONSTRAINT "BORRWR_TRIBAL_AFL" FOREIGN KEY ("TRBL_AFL_CD")
	  REFERENCES "ECRS"."TRBL_AFL_CD" ("TRBL_AFL_CD") ENABLE;
  ALTER TABLE "ECRS"."BORRWR" ADD CONSTRAINT "R_682" FOREIGN KEY ("BORRWR_ID")
	  REFERENCES "ECRS"."PRSN" ("PRSN_ID") ON DELETE CASCADE ENABLE NOVALIDATE;
--------------------------------------------------------
--  Ref Constraints for Table DFCNCY_LTR
--------------------------------------------------------

  ALTER TABLE "ECRS"."DFCNCY_LTR" ADD CONSTRAINT "R_526" FOREIGN KEY ("DFCNCY_LTR_ID")
	  REFERENCES "ECRS"."DOC" ("DOC_ID") ON DELETE CASCADE ENABLE;
  ALTER TABLE "ECRS"."DFCNCY_LTR" ADD CONSTRAINT "R_528" FOREIGN KEY ("LOAN_RVW_ID")
	  REFERENCES "ECRS"."LOAN_RVW" ("LOAN_RVW_ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table DOC
--------------------------------------------------------

  ALTER TABLE "ECRS"."DOC" ADD CONSTRAINT "R_652" FOREIGN KEY ("DOC_UPLD_RSN_CD")
	  REFERENCES "ECRS"."DOC_UPLD_RSN_CD" ("DOC_UPLD_RSN_CD") DISABLE;
--------------------------------------------------------
--  Ref Constraints for Table HOC_LOAN_RVW_PRSNL
--------------------------------------------------------

  ALTER TABLE "ECRS"."HOC_LOAN_RVW_PRSNL" ADD CONSTRAINT "R_602" FOREIGN KEY ("HOC_EMPLYEE_ROLE_CD")
	  REFERENCES "ECRS"."HOC_EMPLYEE_ROLE_CD" ("HOC_EMPLYEE_ROLE_CD") ENABLE;
  ALTER TABLE "ECRS"."HOC_LOAN_RVW_PRSNL" ADD CONSTRAINT "R_679" FOREIGN KEY ("LOAN_RVW_PRSNL_ID")
	  REFERENCES "ECRS"."LOAN_RVW_PRSNL" ("LOAN_RVW_PRSNL_ID") ENABLE;
  ALTER TABLE "ECRS"."HOC_LOAN_RVW_PRSNL" ADD CONSTRAINT "R_680" FOREIGN KEY ("HOC_ID")
	  REFERENCES "ECRS"."HOC" ("HOC_ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table HOC_RVW
--------------------------------------------------------

  ALTER TABLE "ECRS"."HOC_RVW" ADD CONSTRAINT "R_595" FOREIGN KEY ("LOAN_RVW_PRSNL_ID", "HOC_ID")
	  REFERENCES "ECRS"."HOC_LOAN_RVW_PRSNL" ("LOAN_RVW_PRSNL_ID", "HOC_ID") DISABLE;
  ALTER TABLE "ECRS"."HOC_RVW" ADD CONSTRAINT "R_596" FOREIGN KEY ("LOAN_RVW_ID")
	  REFERENCES "ECRS"."LOAN_RVW" ("LOAN_RVW_ID") DISABLE;
  ALTER TABLE "ECRS"."HOC_RVW" ADD CONSTRAINT "R_599" FOREIGN KEY ("LRS_RVW_LVL_CD")
	  REFERENCES "ECRS"."LRS_RVW_LVL_CD" ("LRS_RVW_LVL_CD") DISABLE;
--------------------------------------------------------
--  Ref Constraints for Table HOC_RVWR
--------------------------------------------------------

  ALTER TABLE "ECRS"."HOC_RVWR" ADD CONSTRAINT "R_657" FOREIGN KEY ("LOAN_RVW_PRSNL_ID", "HOC_ID")
	  REFERENCES "ECRS"."HOC_LOAN_RVW_PRSNL" ("LOAN_RVW_PRSNL_ID", "HOC_ID") ON DELETE CASCADE ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table HOC_RVWR_AVLBLTY
--------------------------------------------------------

  ALTER TABLE "ECRS"."HOC_RVWR_AVLBLTY" ADD CONSTRAINT "R_660" FOREIGN KEY ("RVWR_STRT_TEAM_DT", "LOAN_RVW_PRSNL_ID", "HOC_ID")
	  REFERENCES "ECRS"."HOC_RVW_TEAM" ("RVWR_STRT_TEAM_DT", "LOAN_RVW_PRSNL_ID", "HOC_ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table HOC_RVW_TEAM
--------------------------------------------------------

  ALTER TABLE "ECRS"."HOC_RVW_TEAM" ADD CONSTRAINT "R_658" FOREIGN KEY ("LOAN_RVW_PRSNL_ID", "HOC_ID")
	  REFERENCES "ECRS"."HOC_TEAM_LEAD" ("LOAN_RVW_PRSNL_ID", "HOC_ID") ENABLE;
  ALTER TABLE "ECRS"."HOC_RVW_TEAM" ADD CONSTRAINT "R_659" FOREIGN KEY ("LOAN_RVW_PRSNL_ID", "HOC_ID")
	  REFERENCES "ECRS"."HOC_RVWR" ("LOAN_RVW_PRSNL_ID", "HOC_ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table HOC_SPRVSR
--------------------------------------------------------

  ALTER TABLE "ECRS"."HOC_SPRVSR" ADD CONSTRAINT "R_674" FOREIGN KEY ("LOAN_RVW_PRSNL_ID", "HOC_ID")
	  REFERENCES "ECRS"."HOC_LOAN_RVW_PRSNL" ("LOAN_RVW_PRSNL_ID", "HOC_ID") ON DELETE CASCADE ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table LNDR_RESP_LTR
--------------------------------------------------------

  ALTER TABLE "ECRS"."LNDR_RESP_LTR" ADD CONSTRAINT "R_603" FOREIGN KEY ("LNDR_RESP_LTR_ID")
	  REFERENCES "ECRS"."DOC" ("DOC_ID") ON DELETE CASCADE ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table LOAN
--------------------------------------------------------

  ALTER TABLE "ECRS"."LOAN" ADD CONSTRAINT "LOAN_CASE_STAT_CD" FOREIGN KEY ("CASE_STAT_CD")
	  REFERENCES "ECRS"."CASE_STAT_CD" ("CASE_STAT_CD") ENABLE;
  ALTER TABLE "ECRS"."LOAN" ADD CONSTRAINT "R_491" FOREIGN KEY ("LNDR_ID")
	  REFERENCES "ECRS"."LNDR" ("LNDR_ID") ENABLE;
  ALTER TABLE "ECRS"."LOAN" ADD CONSTRAINT "R_492" FOREIGN KEY ("SFH_PRPRTY_ID")
	  REFERENCES "ECRS"."SFH_PRPRTY" ("SFH_PRPRTY_ID") ENABLE;
  ALTER TABLE "ECRS"."LOAN" ADD CONSTRAINT "R_496" FOREIGN KEY ("HOC_ID")
	  REFERENCES "ECRS"."HOC" ("HOC_ID") ENABLE;
  ALTER TABLE "ECRS"."LOAN" ADD CONSTRAINT "R_514" FOREIGN KEY ("INSPCTR_ID")
	  REFERENCES "ECRS"."INSPCTR" ("INSPCTR_ID") ENABLE;
  ALTER TABLE "ECRS"."LOAN" ADD CONSTRAINT "R_579" FOREIGN KEY ("FHA_LOAN_TYPE_CD")
	  REFERENCES "ECRS"."FHA_LOAN_TYPE_CD" ("FHA_LOAN_TYPE_CD") ENABLE;
  ALTER TABLE "ECRS"."LOAN" ADD CONSTRAINT "R_609" FOREIGN KEY ("APRSR_ID")
	  REFERENCES "ECRS"."APRSR" ("APRSR_ID") ENABLE;
  ALTER TABLE "ECRS"."LOAN" ADD CONSTRAINT "R_611" FOREIGN KEY ("HUD_FLD_OFC_CD", "HUD_RGN_CD")
	  REFERENCES "ECRS"."HUD_RGN_FLD_OFC_CD" ("HUD_FLD_OFC_CD", "HUD_RGN_CD") ENABLE;
  ALTER TABLE "ECRS"."LOAN" ADD CONSTRAINT "R_630" FOREIGN KEY ("ADP_CD")
	  REFERENCES "ECRS"."ADP_CD" ("ADP_CD") ENABLE;
  ALTER TABLE "ECRS"."LOAN" ADD CONSTRAINT "R_639" FOREIGN KEY ("LOAN_OFCR_ID")
	  REFERENCES "ECRS"."LOAN_OFCR" ("LOAN_OFCR_ID") DISABLE;
  ALTER TABLE "ECRS"."LOAN" ADD CONSTRAINT "R_641" FOREIGN KEY ("LOAN_ORGNTR_ID")
	  REFERENCES "ECRS"."LOAN_ORGNTR" ("LOAN_ORGNTR_ID") DISABLE;
  ALTER TABLE "ECRS"."LOAN" ADD CONSTRAINT "R_647" FOREIGN KEY ("LOAN_PRPS_CD")
	  REFERENCES "ECRS"."LOAN_PRPS_CD" ("LOAN_PRPS_CD") DISABLE;
  ALTER TABLE "ECRS"."LOAN" ADD CONSTRAINT "R_650" FOREIGN KEY ("UNDRWRTNG_TYPE_CD")
	  REFERENCES "ECRS"."UNDRWRTNG_TYPE_CD" ("UNDRWRTNG_TYPE_CD") DISABLE;
  ALTER TABLE "ECRS"."LOAN" ADD CONSTRAINT "R_651" FOREIGN KEY ("LOAN_ID")
	  REFERENCES "ECRS"."LOAN" ("LOAN_ID") ON DELETE SET NULL DISABLE;
--------------------------------------------------------
--  Ref Constraints for Table LOAN_BORRWR
--------------------------------------------------------

  ALTER TABLE "ECRS"."LOAN_BORRWR" ADD CONSTRAINT "R_645" FOREIGN KEY ("BORRWR_ID")
	  REFERENCES "ECRS"."BORRWR" ("BORRWR_ID") DISABLE;
  ALTER TABLE "ECRS"."LOAN_BORRWR" ADD CONSTRAINT "R_649" FOREIGN KEY ("LOAN_ID")
	  REFERENCES "ECRS"."LOAN" ("LOAN_ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table LOAN_FNDS_TO_CLS
--------------------------------------------------------

  ALTER TABLE "ECRS"."LOAN_FNDS_TO_CLS" ADD CONSTRAINT "R_644" FOREIGN KEY ("LOAN_ID")
	  REFERENCES "ECRS"."LOAN" ("LOAN_ID") DISABLE;
--------------------------------------------------------
--  Ref Constraints for Table LOAN_MORTG
--------------------------------------------------------

  ALTER TABLE "ECRS"."LOAN_MORTG" ADD CONSTRAINT "R_653" FOREIGN KEY ("LOAN_ID")
	  REFERENCES "ECRS"."LOAN" ("LOAN_ID") DISABLE;
--------------------------------------------------------
--  Ref Constraints for Table LOAN_OFCR
--------------------------------------------------------

  ALTER TABLE "ECRS"."LOAN_OFCR" ADD CONSTRAINT "R_608" FOREIGN KEY ("LNDR_ID")
	  REFERENCES "ECRS"."LNDR" ("LNDR_ID") DISABLE;
  ALTER TABLE "ECRS"."LOAN_OFCR" ADD CONSTRAINT "R_683" FOREIGN KEY ("LOAN_OFCR_ID")
	  REFERENCES "ECRS"."PRSN" ("PRSN_ID") ON DELETE CASCADE ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table LOAN_RTNG
--------------------------------------------------------

  ALTER TABLE "ECRS"."LOAN_RTNG" ADD CONSTRAINT "LOAN_RTNG_LOAN_RVW" FOREIGN KEY ("LOAN_RVW_ID")
	  REFERENCES "ECRS"."LOAN_RVW" ("LOAN_RVW_ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table LOAN_RVW
--------------------------------------------------------

  ALTER TABLE "ECRS"."LOAN_RVW" ADD CONSTRAINT "R_521" FOREIGN KEY ("LRS_RVW_TYPE_CD")
	  REFERENCES "ECRS"."LRS_RVW_TYPE_CD" ("LRS_RVW_TYPE_CD") DISABLE;
  ALTER TABLE "ECRS"."LOAN_RVW" ADD CONSTRAINT "R_522" FOREIGN KEY ("LOAN_ID")
	  REFERENCES "ECRS"."LOAN" ("LOAN_ID") DISABLE;
  ALTER TABLE "ECRS"."LOAN_RVW" ADD CONSTRAINT "R_672" FOREIGN KEY ("LRS_STAT_CD")
	  REFERENCES "ECRS"."LRS_STAT_CD" ("LRS_STAT_CD") DISABLE;
--------------------------------------------------------
--  Ref Constraints for Table LOAN_RVW_ACTVTY
--------------------------------------------------------

  ALTER TABLE "ECRS"."LOAN_RVW_ACTVTY" ADD CONSTRAINT "LOAN_RVW_ACTVTY_LOAN" FOREIGN KEY ("LOAN_ID")
	  REFERENCES "ECRS"."LOAN" ("LOAN_ID") ENABLE;
  ALTER TABLE "ECRS"."LOAN_RVW_ACTVTY" ADD CONSTRAINT "LOAN_RVW_ACTVTY_LOAN_RVW" FOREIGN KEY ("LOAN_RVW_ID")
	  REFERENCES "ECRS"."LOAN_RVW" ("LOAN_RVW_ID") ENABLE;
  ALTER TABLE "ECRS"."LOAN_RVW_ACTVTY" ADD CONSTRAINT "R_673" FOREIGN KEY ("ACTVTY_RSN_CD")
	  REFERENCES "ECRS"."ACTVTY_RSN_CD" ("ACTVTY_RSN_CD") DISABLE;
  ALTER TABLE "ECRS"."LOAN_RVW_ACTVTY" ADD CONSTRAINT "R_677" FOREIGN KEY ("DOC_ID")
	  REFERENCES "ECRS"."DOC" ("DOC_ID") DISABLE;
--------------------------------------------------------
--  Ref Constraints for Table LOAN_RVW_PRSNL
--------------------------------------------------------

  ALTER TABLE "ECRS"."LOAN_RVW_PRSNL" ADD CONSTRAINT "R_684" FOREIGN KEY ("LOAN_RVW_PRSNL_ID")
	  REFERENCES "ECRS"."PRSN" ("PRSN_ID") ON DELETE CASCADE ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table LOAN_RVW_SKL_MTRX
--------------------------------------------------------

  ALTER TABLE "ECRS"."LOAN_RVW_SKL_MTRX" ADD CONSTRAINT "R_662" FOREIGN KEY ("LOAN_RVW_PRSNL_ID")
	  REFERENCES "ECRS"."LOAN_RVW_PRSNL" ("LOAN_RVW_PRSNL_ID") DISABLE;
  ALTER TABLE "ECRS"."LOAN_RVW_SKL_MTRX" ADD CONSTRAINT "R_664" FOREIGN KEY ("FHA_LOAN_TYPE_CD")
	  REFERENCES "ECRS"."FHA_LOAN_TYPE_CD" ("FHA_LOAN_TYPE_CD") DISABLE;
--------------------------------------------------------
--  Ref Constraints for Table LRS_DFCNCY
--------------------------------------------------------

  ALTER TABLE "ECRS"."LRS_DFCNCY" ADD CONSTRAINT "R_524" FOREIGN KEY ("LOAN_RVW_ID")
	  REFERENCES "ECRS"."LOAN_RVW" ("LOAN_RVW_ID") DISABLE;
  ALTER TABLE "ECRS"."LRS_DFCNCY" ADD CONSTRAINT "R_594" FOREIGN KEY ("LRS_DFCNCY_CTGRY_CD", "LRS_DFCNCY_TYPE_CD")
	  REFERENCES "ECRS"."LRS_DFCNCY_CTGRY_TYPE" ("LRS_DFCNCY_CTGRY_CD", "LRS_DFCNCY_TYPE_CD") DISABLE;
--------------------------------------------------------
--  Ref Constraints for Table LRS_DFCNCY_CTGRY_TYPE
--------------------------------------------------------

  ALTER TABLE "ECRS"."LRS_DFCNCY_CTGRY_TYPE" ADD CONSTRAINT "R_592" FOREIGN KEY ("LRS_DFCNCY_CTGRY_CD")
	  REFERENCES "ECRS"."LRS_DFCNCY_CTGRY_CD" ("LRS_DFCNCY_CTGRY_CD") DISABLE;
  ALTER TABLE "ECRS"."LRS_DFCNCY_CTGRY_TYPE" ADD CONSTRAINT "R_593" FOREIGN KEY ("LRS_DFCNCY_TYPE_CD")
	  REFERENCES "ECRS"."LRS_DFCNCY_TYPE_CD" ("LRS_DFCNCY_TYPE_CD") DISABLE;
--------------------------------------------------------
--  Ref Constraints for Table LRS_DFCNCY_LTR
--------------------------------------------------------

  ALTER TABLE "ECRS"."LRS_DFCNCY_LTR" ADD CONSTRAINT "R_529" FOREIGN KEY ("LOAN_RVW_ID")
	  REFERENCES "ECRS"."LRS_DFCNCY" ("LOAN_RVW_ID") DISABLE;
  ALTER TABLE "ECRS"."LRS_DFCNCY_LTR" ADD CONSTRAINT "R_530" FOREIGN KEY ("DFCNCY_LTR_ID")
	  REFERENCES "ECRS"."DFCNCY_LTR" ("DFCNCY_LTR_ID") DISABLE;
  ALTER TABLE "ECRS"."LRS_DFCNCY_LTR" ADD CONSTRAINT "R_604" FOREIGN KEY ("LNDR_RESP_LTR_ID")
	  REFERENCES "ECRS"."LNDR_RESP_LTR" ("LNDR_RESP_LTR_ID") DISABLE;
--------------------------------------------------------
--  Ref Constraints for Table POST_ENDRSMNT_TECH_RVW
--------------------------------------------------------

  ALTER TABLE "ECRS"."POST_ENDRSMNT_TECH_RVW" ADD CONSTRAINT "R_558" FOREIGN KEY ("LOAN_RVW_ID")
	  REFERENCES "ECRS"."LOAN_RVW" ("LOAN_RVW_ID") ON DELETE CASCADE DISABLE;
--------------------------------------------------------
--  Ref Constraints for Table PRSN
--------------------------------------------------------

  ALTER TABLE "ECRS"."PRSN" ADD CONSTRAINT "R_109" FOREIGN KEY ("GNDR_CD")
	  REFERENCES "ECRS"."GNDR_CD" ("GNDR_CD") ON DELETE SET NULL DISABLE;
  ALTER TABLE "ECRS"."PRSN" ADD CONSTRAINT "R_110" FOREIGN KEY ("ETHNCTY_CD")
	  REFERENCES "ECRS"."ETHNCTY_CD" ("ETHNCTY_CD") ON DELETE SET NULL DISABLE;
  ALTER TABLE "ECRS"."PRSN" ADD CONSTRAINT "R_634" FOREIGN KEY ("MRTL_STAT_CD")
	  REFERENCES "ECRS"."MRTL_STAT_CD" ("MRTL_STAT_CD") DISABLE;
--------------------------------------------------------
--  Ref Constraints for Table PRSN_RACE
--------------------------------------------------------

  ALTER TABLE "ECRS"."PRSN_RACE" ADD CONSTRAINT "R_635" FOREIGN KEY ("PRSN_ID")
	  REFERENCES "ECRS"."PRSN" ("PRSN_ID") DISABLE;
  ALTER TABLE "ECRS"."PRSN_RACE" ADD CONSTRAINT "R_636" FOREIGN KEY ("RACE_CD")
	  REFERENCES "ECRS"."RACE_CD" ("RACE_CD") DISABLE;
--------------------------------------------------------
--  Ref Constraints for Table RQUST_FOR_DOC
--------------------------------------------------------

  ALTER TABLE "ECRS"."RQUST_FOR_DOC" ADD CONSTRAINT "R_527" FOREIGN KEY ("RQUST_FOR_DOC_ID")
	  REFERENCES "ECRS"."DOC" ("DOC_ID") ON DELETE CASCADE DISABLE;
  ALTER TABLE "ECRS"."RQUST_FOR_DOC" ADD CONSTRAINT "R_567" FOREIGN KEY ("LOAN_RVW_ID")
	  REFERENCES "ECRS"."LOAN_RVW" ("LOAN_RVW_ID") DISABLE;
--------------------------------------------------------
--  Ref Constraints for Table SFH_PRPRTY
--------------------------------------------------------

  ALTER TABLE "ECRS"."SFH_PRPRTY" ADD CONSTRAINT "R_612" FOREIGN KEY ("CNSTRCTN_CD")
	  REFERENCES "ECRS"."CNSTRCTN_CD" ("CNSTRCTN_CD") DISABLE;
--------------------------------------------------------
--  Ref Constraints for Table TEST_CASE_RVW
--------------------------------------------------------

  ALTER TABLE "ECRS"."TEST_CASE_RVW" ADD CONSTRAINT "R_557" FOREIGN KEY ("LOAN_RVW_ID")
	  REFERENCES "ECRS"."LOAN_RVW" ("LOAN_RVW_ID") ON DELETE CASCADE DISABLE;
