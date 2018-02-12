USE LRSDEV_STAGE
go


/*
drop table STAGE_LOAN_SELECTION_IDB_1
drop table STAGE_CHUMS_HECM_CASE_DETAIL
drop table STAGE_LOAN_SELECTION_DEF_DET
drop table STAGE_LOAN_SELECTION_HERMIT
drop table STAGE_LOAN_SELECTION_HUDSTAT
drop table STAGE_LOAN_SELECTION_IDB_2
drop table STAGE_LOAN_SELECTION_DEF_HST
drop table ARCHIVE_HECM_FINANCIAL_TBL
go
*/

CREATE TABLE [STAGE_LOAN_SELECTION_IDB_1]
(
              [CASE_NUMBER]        char(11)  NOT NULL ,
              [ADP_CODE]           varchar(16)  NULL ,
              [AMORT_TYP_CD]       varchar(16)  NULL ,
              [APLCTN_MTHD]        CHAR(10)  NULL ,
              [ARM_ADJ_PRD]        varchar(16)  NULL ,
              [ARM_INDX_EXPCTD_RT] char(2)  NULL ,
              [ARM_INDX_IND]       CHAR(3)  NULL ,
              [ARM_IND]            CHAR(3)  NULL ,
              [ARM_MRGN_AMT]       numeric(6,3)  NULL ,
              [ARM_DT]             datetime  NULL ,
              [BORR_BRTH_DT]       datetime  NULL ,
              [BORR_CNSL_TYP]      varchar(16)  NULL ,
              [BORR_EMPLMNT_IND]   CHAR(3)  NULL ,
              [BORR_GENDER]        CHAR(13)  NULL ,
              [BORR_HSNG_EXP]      INTEGER  NULL ,
              [BORR_TYP]           varchar(16)  NULL ,
              [BUY_DWN_IND]        CHAR(3)  NULL ,
              [CS_ESTAB_DT]        datetime  NULL ,
              [CS_TYP]             varchar(16)  NULL ,
              [CLSNG_DT]           datetime  NULL ,
              [CONST_CD]           varchar(16)  NULL ,
              [CONST_STS_CD]       varchar(16)  NULL ,
              [ENDRSMNT_DT]        datetime  NULL ,
              [PRC_EXCL_CLSNG_AMT] INTEGER  NULL ,
              [PRC_INCL_CLSNG_AMT] INTEGER  NULL ,
              [DPNDNT_CNT]         numeric(3)  NULL ,
              [ENERGY_EFF_MRTG]    CHAR(3)  NULL ,
              [FNCNG_TYP]          varchar(16)  NULL ,
              [GIFT_LTR_AMT]       INTEGER  NULL ,
              [GIFT_LTR_SRC]       varchar(30)  NULL ,
              [GIFT_LTR_TIN]       varchar(13)  NULL ,
              [HLDR_MTGEE10_A43C]  CHAR(10)  NULL ,
              [HLDR_MTGEE5_A43]    CHAR(15)  NULL ,
              [HSNG_PGM_CD]        varchar(16)  NULL ,
              [INSRNC_STATUS_CD]   varchar(16)  NULL ,
              [INT_RT]             numeric(5,3)  NULL ,
              [MISC_LNDR_INSRNC_IND] CHAR(13)  NULL ,
              [LOAN_PRPS]          CHAR(3)  NULL ,
              [LTV_CAT]            varchar(16)  NULL ,
              [LTV_CAT_NEW]        varchar(16)  NULL ,
              [LTV_CAT_OLD]        varchar(16)  NULL ,
              [RATIO_LOAN_TO_VL_NEW] numeric(7,2)  NULL ,
              [RATIO_LOAN_TO_VL_OLD] numeric(7,2)  NULL ,
              [CURR_MNTHLY_MIP]    numeric(7,2)  NULL ,
              [MISC_AUS_DCSN_CD]   varchar(16)  NULL ,
              [MISC_AUS_IND]       CHAR(3)  NULL ,
              [RANDOM_NUMBER]      numeric(3)  NULL ,
              [MORT_EXCLD_FNCD_MIP] numeric(9,2)  NULL ,
              [LIV_UNITS]          numeric(3)  NULL ,
              [ORIG_MRTG_AMT]      numeric(9,2)  NULL ,
              [ORGNTNG_MTGEE10_ID] CHAR(10)  NULL ,
              [TYP_ORGNTNG_MTGEE]  varchar(16)  NULL ,
              [PST_RVW_DCSN_CD]    varchar(16)  NULL ,
              [PRE_CLSNG_IND]      CHAR(3)  NULL ,
              [PRNCPL_RDCTN_AMT]   numeric(9,2)   NULL ,
              [PRCSNG_TYP]         varchar(16)  NULL ,
              [PROG_ID_F17]        varchar(16)  NULL ,
              [DT_ACQ]             datetime  NULL ,
              [PROP_ADDR_ST]       varchar(10)  NULL ,
              [PRPRTY_APRSL_VL]    numeric(9,2)   NULL ,
              [PRPRTY_CNVRSN_TYP]  varchar(16)  NULL ,
              [PD_STRMLN_FLG]      CHAR(3)  NULL ,
              [RATIO_ORE_TEI]      numeric(5,2)   NULL ,
              [RATIO_FIX_TEI]      numeric(5,2)   NULL ,
              [PTI_CAT]            char(5)  NULL ,
              [RFNC_CD]            varchar(16)  NULL ,
              [RFNC_IND]           CHAR(3)  NULL ,
              [RQRD_INVEST]        numeric(9,2)   NULL ,
              [NBRHD_CD]           varchar(16)  NULL ,
              [SCNDRY_FNC_SRC]     varchar(16)  NULL ,
              [SELLER_CNTRBTN]     numeric(9,2)   NULL ,
              [OLD_SRVCR_MTGEE]    CHAR(5)  NULL ,
              [SRVCR_MTGEE10_A43C] CHAR(10)  NULL ,
              [SRVCR_MTGEE5_A43]   CHAR(5)  NULL ,
              [SFPCS_MTGEE_ID]     CHAR(5)  NULL ,
              [SOA_CD]             varchar(16)  NULL ,
              [SPNSR_MTGEE10_ID]   CHAR(10)  NULL ,
              [TRV_SLCT_RSN_CD]    varchar(16)  NULL ,
              [TERM]               INTEGER  NULL ,
              [TERM_TYP_CD]        varchar(16)  NULL ,
              [TOT_ANN_EFF_INCM]   numeric(9,2)   NULL ,
              [TOT_ASSETS]         numeric(9,2)   NULL ,
              [TOT_CLSNG_CSTS]     numeric(9,2)   NULL ,
              [TOT_FIXED_PYMT]     numeric(9,2)   NULL ,
              [TOT_MNTHLY_EFF_INCM] numeric(9,2)   NULL ,
              [TOT_MNTHLY_MTG_PYMT] numeric(9,2)   NULL ,
              [TOT_ACQ_COSTS]      numeric(9,2)   NULL ,
              [UFMIP_PD_AMT]       numeric(9,2)   NULL ,
              [UFMIP_PD_CASH]      numeric(9,2)   NULL ,
              [UFMIP_EARNED_CURR_MM] numeric(9,2)   NULL ,
              [UFMIP_FACTOR]       numeric(7,5)   NULL ,
              [VAL_PLUS_CLSNG]     integer   NULL,
              [aprsl_cmpltn_dt]    datetime null,
              [CASHOUT_REFI_IND]   char(1),
              [UNDERWRITER_ID]     char(4),
              [UNDRWRTING_MTGEE5]  char(5),
			        [MASKED_IND]         CHAR(1)  NULL
              )
go



CREATE TABLE [STAGE_LOAN_SELECTION_IDB_2]
(
              [CASE_NUMBER]        char(11)  NOT NULL ,
              [ALLOW_CLSG_COST]    INTEGER  NULL ,
              [BSMT_CD]            varchar(16)  NULL ,
              [FCTRY_FBRCT]        CHAR(1)  NULL ,
              [FHAC_ADDR_CHG]      CHAR(1)  NULL ,
              [MIP_FINANCED_IND]   CHAR(1)  NULL ,
              [NBRHD_PCT_OWNED]    INTEGER  NULL ,
              [NBRHD_PRICE]        INTEGER  NULL ,
              [NBR_BTHRMS]         numeric(3,1)  NULL ,
              [NBR_BDRM]           numeric(3)  NULL ,
              [NBR_RMS]            numeric(3)  NULL ,
              [RCV_SALE_DT]        datetime  NULL ,
              [SBDVSN_SPOT_LOT]    varchar(16)  NULL ,
              [PRIOR_SALE_RQRD_IND] CHAR(1)  NULL ,
              [SEND_MIC_IND]       CHAR(1)  NULL ,
              [PCT_1_FMLY]         INTEGER  NULL,
              [CCT_REINS_DT]       datetime,
			        [MASKED_IND]         CHAR(1)  NULL
       )
go


CREATE TABLE [STAGE_LOAN_SELECTION_DEF_DET]
(
              [CASE_NUMBER]           char(11)  NULL ,
              [CURR_DFLT_90DAY_IND] CHAR(1)  NULL ,
              [CURR_DFLT_ASGNMNT_DT]    datetime  NULL ,
              [CURR_DFLT_CRTN_DT]       datetime  NULL ,
              [CURR_DFLT_CYC_DT]   datetime  NULL ,
              [CURR_DFLT_EPISODE_NBR] INTEGER  NULL ,
              [CURR_DFLT_MM_CYC_DT] INTEGER  NULL ,
              [CURR_DFLT_RSN_CD]   varchar(16)  NULL ,
              [CURR_DFLT_STS_CD]   varchar(16)  NULL ,
              [CURR_DFLT_STS_DT]   datetime  NULL ,
              [CURR_DFLT_STS_SUMMARY_CD] varchar(16)  NULL ,
              [CURR_DFLT_TRNSCTN_DT]    datetime  NULL ,
              [END_CD]             varchar(16)  NULL ,
              [FRCLSR_IND]         CHAR(1)  NULL ,
              [FT_IN_EPS_2MNTH_DELQ_DT] datetime  NULL ,
              [FT_IN_EPS_2MNTH_DELQ_IND] CHAR(1)  NULL ,
              [FT_IN_EPS_3MNTH_DELQ_DT] datetime  NULL ,
              [FT_IN_EPS_3MNTH_DELQ_IND] CHAR(1)  NULL ,
              [LOSSMIT_CD]         varchar(16)  NULL ,
              [OCPNCY_STS]         varchar(16)  NULL ,
              [PYMTS_BFR_FRST_MISSED_PYMT] numeric(3)  NULL,
			  [MASKED_IND]         CHAR(1)  NULL
       )
go


CREATE TABLE [STAGE_LOAN_SELECTION_DEF_HST]
(
              [CASE_NUMBER]        char(11)  NULL ,
              [BNKRPTCY_CD]        varchar(16)  NULL ,
              [BNKRPTCY_DT]        datetime  NULL ,
              [BORR_NM]            varchar(100)  NULL ,
              [DFLT_90DAY_IND]     CHAR(1)  NULL ,
              [DFLT_CYC_DT]        datetime  NULL ,
              [DFLT_EPISODE_NBR]   INTEGER  NULL ,
              [DFLT_HIST_FT_IN_EPS_2M_DELQ_DT] datetime  NULL ,
              [DFLT_HIST_LOSSMIT_CD] varchar(16)  NULL ,
              [DFLT_MM_CYC_DT]     integer  NULL ,
              [DFLT_RSN_CD]        varchar(16)  NULL ,
              [DFLT_SRVCR]         CHAR(5)  NULL ,
              [DFLT_STS_CD]        varchar(16)  NULL ,
              [DFLT_STS_DT]        datetime  NULL ,
              [DFLT_STS_SUMMARY_CD] varchar(16)  NULL ,
              [DFLT_TRNSCTN_DT]    datetime  NULL ,
              [DERIVED_OPTN_USED_CD] varchar(16)  NULL ,
              [DFLT_HIST_FT_IN_EPS_3M_DELQ_DT] datetime  NULL ,
              [FTE_2MNTH_DELQ_DT]  datetime  NULL ,
              [LOAN_NBR]           varchar(30)  NULL ,
              [NBR_MONTHS]         numeric(3)  NULL ,
              [OCPNCY_STS_CD]      varchar(16)  NULL ,
              [OCPNCY_STS_DT]      datetime  NULL ,
              [OLDST_UNPD_DT]      datetime  NULL ,
              [SQNC_NBR]           INTEGER  NULL ,
              [STRT_STOP_EPS_IND]  CHAR(2)  NULL ,
              [TRNSMSN_TYP]        varchar(16)  NULL ,
              [UNPD_BAL]           INTEGER  NULL,
			        [MASKED_IND]         CHAR(1)  NULL
       )
go


CREATE TABLE [STAGE_LOAN_SELECTION_HUDSTAT]
(
     [CASE_NUMBER]        char(11)  NULL ,
     [RATIO_TMP_TEI]      numeric(7,2)  NULL,
	   [MASKED_IND]         CHAR(1)  NULL
    )
go


CREATE TABLE [STAGE_LOAN_SELECTION_HERMIT]
(
	[CASE_NUMBER]           char(11)  NULL ,
	[ADDTNL_10PCT_IPL_USAGE_AMT] INTEGER  NULL ,
	[ADDTNL_10PCT_IPL_USAGE_IND] CHAR(1)  NULL ,
	[INIT_DISBURSEMENT_LIMIT] INTEGER  NULL ,
	[MNDTRY_OBLGTNS_AMT] INTEGER  NULL ,
	[TAXES_INSRNC_FRST_YR_AMT] INTEGER  NULL,
	[MASKED_IND]         CHAR(1)  NULL
)
go

CREATE TABLE [STAGE_CHUMS_HECM_CASE_DETAIL]
(
[CASE_NUMBER]        char(11)  NULL ,
[CORP_ADVNC_AMT]     numeric(9,2)  NULL,
[HECM_ASSETS]        INTEGER NULL,
[HECM_COUNSEL_DT]    datetime NULL,
[HECM_LIENS]         INTEGER  NULL ,
[HECM_MNTHLY_INC]    INTEGER  NULL ,
[HECM_PRNCPL_LMT]    INTEGER  NULL ,
[INIT_FEE]           numeric(9,2)  NULL ,
[LOAN_PRPS_FRWD_PYMT_IND] CHAR(1)  NULL ,
[LOAN_PRPS_IMPRVMNT_IND] CHAR(1)  NULL ,
[LOAN_PRPS_INCM_IND] CHAR(1)  NULL ,
[LOAN_PRPS_INSRNC_IND] CHAR(1)  NULL ,
[LOAN_PRPS_LEISURE_IND] CHAR(1)  NULL ,
[LOAN_PRPS_MEDCL_IND] CHAR(1)  NULL ,
[LOAN_PRPS_OTHR_IND] CHAR(1)  NULL ,
[LOAN_PRPS_TAXES_IND] CHAR(1)  NULL ,
[LOAN_PRPS_TEXT]     varchar(100)  NULL ,
[LOAN_TYP]          varchar(16)  NULL ,
[MNTHLY_SET_ASIDE]   INTEGER  NULL ,
[ORGNTNG_MTGEE5]     CHAR(5)  NULL ,
[MASKED_IND]         CHAR(1)  NULL
)
go

create table ARCHIVE_HECM_FINANCIAL_TBL
(
CASE_NUMBER                    varchar(11)  ,
CF_EQUIVICAL_ASSETS_IND        CHAR(1)     null ,
CF_EXPECTED_SSI_IND            CHAR(1)     null ,
CF_HECM_SUFFICIENT_IND         CHAR(1)     null ,
CF_IMPUTED_INCOME_IND          CHAR(1)     null ,
CF_NBS_INCOME_IND              CHAR(1)     null ,
CF_OT_INCOME_IND               CHAR(1)     null ,
CF_OTHER_INCOME_IND            CHAR(1)     null ,
CF_PROP_PMT_HIST_IND           CHAR(1)     null ,
CONDO_FEE_CURR_IND             CHAR(1)     null ,
CONDO_FEE_DELINQ_IND           CHAR(1)     null ,
CREATE_DATE                    datetime    null ,
CREATE_ID                      CHAR(6)     null ,
LE_COMPOUND_RATE               numeric(5,3) null ,
LE_EXPECTED_RATE               numeric(5,3) null ,
LE_PROJECTED_AMT               numeric(9,2) null ,
LE_SETASIDE_AMT                numeric(9,2) null ,
LE_TALC_MONTHS                 numeric(38)  null ,
LESA_FUNDING_AMT               numeric(9,2) null ,
LESA_FUNDING_TYPE              CHAR(1)     null ,
ME_DEBT_AMT                    numeric(9,2) null ,
ME_OTHER_AMT                   numeric(9,2) null ,
ME_RE_AMT                      numeric(9,2) null ,
ME_TOTAL_AMT                   numeric(9,2) null ,
MI_IMPUTED_AMT                 numeric(9,2) null ,
MI_OTHER_AMT                   numeric(9,2) null ,
MI_TOTAL_AMT                   numeric(9,2) null ,
OTHER_DEBT_CURR_IND            CHAR(1)     null ,
OTHER_DEBT_LATE_PMT_IND        CHAR(1)     null ,
PC_CONDO_FEE_AMT               numeric(9,2) null ,
PC_FLOOD_INS_AMT               numeric(9,2) null ,
PC_HAZ_INS_AMT                 numeric(9,2) null ,
PC_OTHER_AMT                   numeric(9,2) null ,
PC_RE_TAX_AMT                  numeric(9,2) null ,
PC_SETASIDE_TOT_AMT            numeric(9,2) null ,
PC_TOTAL_AMT                   numeric(9,2) null ,
RE_DEBT_CURR_IND               CHAR(1)     null ,
RE_DEBT_LATE_PMT_IND           CHAR(1)     null ,
RE_TAX_CURR_IND                CHAR(1)     null ,
RE_TAX_DELINQ_IND              CHAR(1)     null ,
REVOLVE_DEBT_CURR_IND          CHAR(1)     null ,
REVOLVE_DEBT_LATE_PMT_IND      CHAR(1)     null ,
RI_FAMILY_SIZE                 numeric(38)  null ,
RI_SHORTFALL_AMT               numeric(9,2) null ,
RI_STANDARD_AMT                numeric(9,2) null ,
RI_TOTAL_AMT                   numeric(9,2) null ,
UPDATE_DATE                    datetime    null ,
UPDATE_ID                      CHAR(6)     null ,
MASKED_IND                     CHAR(1)     null
)        
go

create table STAGE_IDB_3
(
CASE_NUMBER                    varchar(11)  ,
gift_ltr_2_amt                 INTEGER  NULL,
MASKED_IND                     CHAR(1)     null
)
go