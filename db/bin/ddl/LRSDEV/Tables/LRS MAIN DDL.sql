use LRSDEV
go

CREATE TABLE [ASSIGNMENT_TYPE_ADMIN]
( 
	[ASSIGNMENT_TYPE_CD] varchar(16)  NOT NULL ,
	[ASSIGNMENT_TYPE_CATEGORY] varchar(16)  NULL ,
	[ASSIGNMENT_TYPE_NAME] varchar(16)  NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [ASSIGNMENT_TYPE_ADMIN]
	ADD CONSTRAINT [ASSIGNMENT_TYPE_ADMIN_PK] PRIMARY KEY  NONCLUSTERED ([ASSIGNMENT_TYPE_CD] ASC)
go

CREATE TABLE [COMM_ACTIVITY]
( 
	[COMMUNICATION_ID]   CHAR(36)  NOT NULL ,
	[CORRESPONDENCE_TYPE] varchar(16)  NULL ,
	[RECEIVED_DT]        datetime  NULL ,
	[SENT_DT]            datetime  NULL ,
	[MTGEE5]             CHAR(5)  NULL ,
	[REVIEW_ID]          varchar(14)  NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [COMM_ACTIVITY]
	ADD CONSTRAINT [COMM_ACTIVITY_PK] PRIMARY KEY  NONCLUSTERED ([COMMUNICATION_ID] ASC)
go

CREATE TABLE [COMM_TEMPLATE]
( 
	[TEMPLATE_ID]        varchar(16)  NOT NULL ,
	[TEMPLATE_CONTENT]   text  NULL ,
	[TEMPLATE_PRPS]      varchar(16)  NULL ,
	[TEMPLATE_SUB_PRPS]  varchar(16)  NULL ,
	[TEMPLATE_TYPE]      varchar(16)  NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [COMM_TEMPLATE]
	ADD CONSTRAINT [COMM_TEMPLATE_PK] PRIMARY KEY  NONCLUSTERED ([TEMPLATE_ID] ASC)
go

CREATE TABLE [COMM_TEMPLATE_AUDIT]
( 
	[AUDIT_TMSTMP]       datetime  NOT NULL ,
	[TEMPLATE_ID]        varchar(16)  NOT NULL ,
	[TEMPLATE_CONTENT]   text  NULL ,
	[TEMPLATE_PRPS]      varchar(16)  NULL ,
	[TEMPLATE_SUB_PRPS]  varchar(16)  NULL ,
	[TEMPLATE_TYPE]      varchar(16)  NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [COMM_TEMPLATE_AUDIT]
	ADD CONSTRAINT [COMM_TEMPLATE_AUDIT_PK] PRIMARY KEY  NONCLUSTERED ([AUDIT_TMSTMP] ASC,[TEMPLATE_ID] ASC)
go

CREATE TABLE [DEFECT]
( 
	[DEFECT_CD]          varchar(16)  NOT NULL ,
	[DESCRIPTION]        varchar(150)  NULL ,
	[REVIEW_CATEGORY]    varchar(16)  NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [DEFECT]
	ADD CONSTRAINT [DEFECT_PK] PRIMARY KEY  NONCLUSTERED ([DEFECT_CD] ASC)
go

CREATE TABLE [DEFECT_CAUSE]
( 
	[DEFECT_CAUSE_CD]    varchar(16)  NOT NULL ,
	[DEFECT_CD]          varchar(16)  NOT NULL ,
	[DESCRIPTION]        varchar(150)  NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [DEFECT_CAUSE]
	ADD CONSTRAINT [DEFECT_CAUSE_PK] PRIMARY KEY  NONCLUSTERED ([DEFECT_CAUSE_CD] ASC,[DEFECT_CD] ASC)
go

CREATE TABLE [DEFECT_SOURCE]
( 
	[DEFECT_CD]          varchar(16)  NOT NULL ,
	[DFCT_SOURCE_CD]     varchar(16)  NOT NULL ,
	[DESCRIPTION]        varchar(150)  NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [DEFECT_SOURCE]
	ADD CONSTRAINT [DEFECT_SOURCE_PK] PRIMARY KEY  NONCLUSTERED ([DEFECT_CD] ASC,[DFCT_SOURCE_CD] ASC)
go

CREATE TABLE [DELIV_PARMS_BINDER]
( 
	[RQST_DOCS_SOURCE_CD] varchar(16)  NOT NULL ,
	[DAYS_TO_SATISFY]    numeric(3)  NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [DELIV_PARMS_BINDER]
	ADD CONSTRAINT [DELIV_PARMS_BINDER_PK] PRIMARY KEY  NONCLUSTERED ([RQST_DOCS_SOURCE_CD] ASC)
go

CREATE TABLE [DICT_METADATA_ENTITY]
( 
	[ENTITY_NAME]        varchar(60)  NOT NULL ,
	[DB_VIEW_OR_TABLE]   varchar(30)  NULL ,
	[DESCRIPTION]        text  NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [DICT_METADATA_ENTITY]
	ADD CONSTRAINT [DICT_META_ENTITY_PK] PRIMARY KEY  NONCLUSTERED ([ENTITY_NAME] ASC)
go

CREATE TABLE [DICT_METADATA_FIELD]
( 
	[ENTITY_NAME]        character varying(60)  NOT NULL ,
	[FIELD_NAME]         varchar(60)  NOT NULL ,
	[DB_COLUMN]          varchar(30)  NULL ,
	[DESCRIPTION]        text  NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [DICT_METADATA_FIELD]
	ADD CONSTRAINT [DICT_META_FIELD_PK] PRIMARY KEY  NONCLUSTERED ([ENTITY_NAME] ASC,[FIELD_NAME] ASC)
go

CREATE TABLE [EXCEPTION]
( 
	[EXCEPTION_ID]       CHAR(36)  NOT NULL ,
	[BATCH_ID]           varchar(16)  NULL ,
	[DETAILS]            text  NULL ,
	[REASON]             varchar(16)  NULL ,
	[STATUS]             varchar(16)  NULL ,
	[EXCEPTION_TYPE]     varchar(16)  NULL ,
	[SELECTION_ID]       CHAR(16)  NULL ,
	[REVIEW_ID]          varchar(14)  NULL ,
	[SLCTN_REQ_ID]       CHAR(36)  NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [EXCEPTION]
	ADD CONSTRAINT [EXCEPTION_PK] PRIMARY KEY  NONCLUSTERED ([EXCEPTION_ID] ASC)
go

CREATE TABLE [INCREASED_SELECT_LENDER]
( 
	[MTGEE5]             CHAR(5)  NOT NULL ,
	[COMMENTS]           text  NULL ,
	[EFFECTIVE_DATE]     datetime  NULL ,
	[TARGET_FWD_PCT]     INTEGER  NULL ,
	[TARGET_HECM_PCT]    INTEGER  NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [INCREASED_SELECT_LENDER]
	ADD CONSTRAINT [INCREASED_SELECT_LENDER_PK] PRIMARY KEY  NONCLUSTERED ([MTGEE5] ASC)
go

CREATE TABLE [RATING]
( 
	[RATING_CD]          varchar(16)  NOT NULL ,
	[DESCRIPTION]        varchar(150)  NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [RATING]
	ADD CONSTRAINT [RATING_PK] PRIMARY KEY  NONCLUSTERED ([RATING_CD] ASC)
go

CREATE TABLE [DEFECT_SEVERITY_TIER]
( 
	[DEFECT_CAUSE_CD]    varchar(16)  NOT NULL ,
	[DEFECT_CD]          varchar(16)  NOT NULL ,
	[RATING_CD]          varchar(16)  NULL ,
	[SEVERITY_TIER_CD]   varchar(16)  NOT NULL ,
	[DFCT_SOURCE_CD]     varchar(16)  NOT NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [DEFECT_SEVERITY_TIER]
	ADD CONSTRAINT [DEFECT_SEVERITY_TIER_PK] PRIMARY KEY  NONCLUSTERED ([DEFECT_CD] ASC,[DFCT_SOURCE_CD] ASC,[DEFECT_CAUSE_CD] ASC,[SEVERITY_TIER_CD] ASC)
go

CREATE TABLE [INCREASED_SELECT_UNDERWRITER]
( 
	[UNDERWRITER_ID]     varchar(16)  NOT NULL ,
	[COMMENTS]           text  NULL ,
	[EFFECTIVE_DATE]     datetime  NULL ,
	[FWD_PERCENT]        numeric(3)  NULL ,
	[HECM_PERCENT]       numeric(3)  NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [INCREASED_SELECT_UNDERWRITER]
	ADD CONSTRAINT [INCREASED_SELECT_UNDERWRITE_PK] PRIMARY KEY  NONCLUSTERED ([UNDERWRITER_ID] ASC)
go

CREATE TABLE [LENDE_PARMS_FINDINGS]
( 
	[AUDIT_FINDING]      varchar(2000)  NOT NULL ,
	[CASE_NUMBER]        char(11)  NOT NULL ,
	[CORRECTIVE_ACTION]  varchar(2000)  NULL ,
	[EXPLANATION]        varchar(2000)  NULL ,
	[PARTY_TO_FINDING]   varchar(100)  NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [LENDE_PARMS_FINDINGS]
	ADD CONSTRAINT [LENDE_PARMS_FINDINGS_PK] PRIMARY KEY  NONCLUSTERED ([AUDIT_FINDING] ASC,[CASE_NUMBER] ASC)
go

CREATE TABLE [LOAN_SELECTION]
( 
	[SELECTION_ID]       INT IDENTITY(1,1)  NOT NULL ,
	[BATCH_ID]           varchar(16)  NULL ,
	[CASE_NUMBER]        char(11)  NULL ,
	[RVW_LOCATION_ID]    varchar(16)  NULL ,
	[DISTRIBUTION_DT]    datetime  NULL ,
	[MTGEE5]             CHAR(5)  NULL ,
	[DUE_DATE]           datetime  NULL ,
	[PREF_RVW_LOCATION]  varchar(16)  NULL ,
	[PRIMARY_SELECTION_REASON] varchar(16)  NULL ,
	[RECEIVED_DT]        datetime  NULL ,
	[RQST_DOCS_SOURCE_CD] varchar(16)  NULL ,
	[REQUESTED_DT_TM]    datetime  NULL ,
	[REVIEW_TYPE]        varchar(16)  NULL ,
	[SELECTION_DT]       datetime  NULL ,
	[STATUS]             varchar(16)  NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [LOAN_SELECTION]
	ADD CONSTRAINT [LOAN_SELECTION_PK] PRIMARY KEY  NONCLUSTERED ([SELECTION_ID] ASC)
go

CREATE TABLE [LOAN_SELECTION_CASE_SUMMARY]
( 
	[APRSL_CMPLTN_DT]    datetime  NULL ,
	[CASE_NUMBER]        char(11)  NOT NULL ,
	[SELECTION_ID]       INT NOT NULL ,
	[PREQUAL_DT]         datetime  NULL ,
	[ADDTNL_10PCT_IPL_USAGE_AMT] INTEGER  NULL ,
	[ADDTNL_10PCT_IPL_USAGE_IND] CHAR(1)  NULL ,
	[ADP_CODE]           varchar(16)  NULL ,
	[ALLOW_CLSG_COST]    INTEGER  NULL ,
	[AMORT_TYP_CD]       varchar(16)  NULL ,
	[APLCTN_MTHD]        CHAR(10)  NULL ,
	[ARM_ADJ_PRD]        varchar(16)  NULL ,
	[ARM_INDX_EXPCTD_RT] char(2)  NULL ,
	[ARM_INDX_IND]       CHAR(3)  NULL ,
	[ARM_IND]            CHAR(3)  NULL ,
	[ARM_MRGN_AMT]       numeric(6,3)  NULL ,
	[ARM_DT]             datetime  NULL ,
	[COUNT_AUS]          INTEGER  NULL ,
	[AUS_SCORE_CODE]     varchar(16)  NULL ,
	[BSMT_CD]            varchar(16)  NULL ,
	[BNKRPTCY_CD]        varchar(16)  NULL ,
	[BNKRPTCY_DT]        datetime  NULL ,
	[BORR_NM]            varchar(100)  NULL ,
	[BORR_BRTH_DT]       datetime  NULL ,
	[BORR_CNSL_TYP]      varchar(16)  NULL ,
	[BORR_EMPLMNT_IND]   CHAR(3)  NULL ,
	[BORR_GENDER]        CHAR(13)  NULL ,
	[BORR_HSNG_EXP]      INTEGER  NULL ,
	[BORR_TYP]           varchar(16)  NULL ,
	[BUY_DWN_IND]        CHAR(3)  NULL ,
	[CS_ESTAB_DT]        datetime  NULL ,
	[CASE_NBR]           char(11)  NULL ,
	[CCT_REINS_DT]       datetime  NULL ,
	[CS_TYP]             varchar(16)  NULL ,
	[CASHOUT_REFI_IND]   CHAR(1)  NULL ,
	[CLAIM_TYPE]         varchar(16)  NULL ,
	[CLSNG_DT]           datetime  NULL ,
	[CF_EQUIVICAL_ASSETS_IND] CHAR(1)  NULL ,
	[CF_EXPECTED_SSI_IND] CHAR(1)  NULL ,
	[CF_HECM_SUFFICIENT_IND] CHAR(1)  NULL ,
	[CF_IMPUTED_INCOME_IND] CHAR(1)  NULL ,
	[CF_NBS_INCOME_IND]  CHAR(1)  NULL ,
	[CF_OT_INCOME_IND]   CHAR(1)  NULL ,
	[CF_OTHER_INCOME_IND] CHAR(1)  NULL ,
	[CF_PROP_PMT_HIST_IND] CHAR(1)  NULL ,
	[CONDO_FEE_CURR_IND] CHAR(1)  NULL ,
	[CONDO_FEE_DELINQ_IND] CHAR(1)  NULL ,
	[CONST_CD]           varchar(16)  NULL ,
	[CONST_STS_CD]       varchar(16)  NULL ,
	[PRC_EXCL_CLSNG_AMT] INTEGER  NULL ,
	[PRC_INCL_CLSNG_AMT] INTEGER  NULL ,
	[CORP_ADVNC_AMT]     INTEGER  NULL ,
	[CURR_DFLT_90DAY_IND] CHAR(1)  NULL ,
	[DFLT_ASGNMNT_DT]    datetime  NULL ,
	[DFLT_CRTN_DT]       datetime  NULL ,
	[CURR_DFLT_CYC_DT]   datetime  NULL ,
	[CURR_DFLT_EPISODE_NBR] INTEGER  NULL ,
	[CURR_DFLT_MM_CYC_DT] datetime  NULL ,
	[CURR_DFLT_RSN_CD]   varchar(16)  NULL ,
	[CURR_DFLT_STS_CD]   varchar(16)  NULL ,
	[CURR_DFLT_STS_DT]   datetime  NULL ,
	[CURR_DFLT_STS_IND]  CHAR(1)  NULL ,
	[CURR_DFLT_STS_SUMMARY_CD] varchar(16)  NULL ,
	[DAYS_IN_DEFAULT]    INTEGER  NULL ,
	[DFLT_90DAY_IND]     CHAR(1)  NULL ,
	[DFLT_CYC_DT]        datetime  NULL ,
	[DFLT_EPISODE_NBR]   INTEGER  NULL ,
	[FT_IN_EPS_2MNTH_DELQ_DT] datetime  NULL ,
	[DFLT_HIST_LOSSMIT_CD] varchar(16)  NULL ,
	[DFLT_MM_CYC_DT]     INTEGER  NULL ,
	[DFLT_RSN_CD]        varchar(16)  NULL ,
	[DFLT_SRVCR]         CHAR(5)  NULL ,
	[DFLT_STS_CD]        varchar(16)  NULL ,
	[DFLT_STS_DT]        datetime  NULL ,
	[DFLT_STS_SUMMARY_CD] varchar(16)  NULL ,
	[DFLT_TRNSCTN_DT]    datetime  NULL ,
	[DPNDNT_CNT]         numeric(3)  NULL ,
	[DERIVED_OPTN_USED_CD] varchar(16)  NULL ,
	[DFLT_HIST_FT_IN_EPS_3M_DELQ_DT] datetime  NULL ,
	[DIR_LENDING_BRANCH_IND] varchar(16)  NULL ,
	[EARLY_CLAIM_IND]    CHAR(1)  NULL ,
	[EARLY_DEFAULT_IND]  CHAR(1)  NULL ,
	[EFFECTIVE_FICO_SCORE] INTEGER  NULL ,
	[END_CD]             varchar(16)  NULL ,
	[ENDRSMNT_DT]        datetime  NULL ,
	[ENDRSMNT_RVW_PRSNNL_ID] varchar(32)  NULL ,
	[ENERGY_EFF_MRTG]    CHAR(3)  NULL ,
	[FCTRY_FBRCT]        CHAR(1)  NULL ,
	[FEMA_FLOOD_AREA_IND] CHAR(1)  NULL ,
	[FHAC_ADDR_CHG]      CHAR(1)  NULL ,
	[FICO_SCORE]         INTEGER  NULL ,
	[FNCNG_TYP]          varchar(16)  NULL ,
	[FRCLSR_IND]         CHAR(1)  NULL ,
	[FT_IN_EPS_2MNTH_DELQ_IND] CHAR(1)  NULL ,
	[FT_IN_EPS_3MNTH_DELQ_DT] datetime  NULL ,
	[FT_IN_EPS_3MNTH_DELQ_IND] CHAR(1)  NULL ,
	[FTE_2MNTH_DELQ_DT]  datetime  NULL ,
	[GIFT_LTR_AMT]       INTEGER  NULL ,
	[GIFT_LTR_2_AMT]     INTEGER  NULL ,
	[GIFT_LTR_SRC]       varchar(30)  NULL ,
	[GIFT_LTR_TIN]       varchar(13)  NULL ,
	[HARDCOPY_DOCS_IND]  CHAR(1)  NULL ,
	[HECM_ASSETS]        INTEGER  NULL ,
	[HECM_COUNSEL_DT]    datetime  NULL ,
	[CREATE_DATE]        datetime  NULL ,
	[CREATE_PRSNNL_ID]   varchar(30)  NULL ,
	[UPDATE_DATE]        datetime  NULL ,
	[UPDATE_PRSNNL_ID]   varchar(30)  NULL ,
	[HECM_LIENS]         INTEGER  NULL ,
	[HECM_MNTHLY_INC]    INTEGER  NULL ,
	[HECM_PRNCPL_LMT]    INTEGER  NULL ,
	[HLDR_MTGEE10_A43C]  CHAR(10)  NULL ,
	[HLDR_MTGEE5_A43]    CHAR(15)  NULL ,
	[HSNG_PGM_CD]        varchar(16)  NULL ,
	[INIT_DISBURSEMENT_LIMIT] INTEGER  NULL ,
	[INIT_FEE]           INTEGER  NULL ,
	[INIT_MIP_FACTOR]    INTEGER  NULL ,
	[INSRNC_STATUS_CD]   varchar(16)  NULL ,
	[INT_RT]             numeric(5,3)  NULL ,
	[LAST_CS_SCORE_DT]   datetime  NULL ,
	[MISC_LNDR_INSRNC_IND] CHAR(13)  NULL ,
	[LE_COMPOUND_RATE]   INTEGER  NULL ,
	[LE_EXPECTED_RATE]   INTEGER  NULL ,
	[LE_PROJECTED_AMT]   INTEGER  NULL ,
	[LESA_FUNDING_AMT]   INTEGER  NULL ,
	[LESA_FUNDING_TYPE]  varchar(16)  NULL ,
	[LE_SETASIDE_AMT]    INTEGER  NULL ,
	[LE_TALC_MONTHS]     INTEGER  NULL ,
	[MONTHLY_P_I]        INTEGER  NULL ,
	[LOAN_NBR]           varchar(30)  NULL ,
	[LOAN_PRPS]          CHAR(3)  NULL ,
	[LOAN_PRPS_FRWD_PYMT_IND] CHAR(1)  NULL ,
	[LOAN_PRPS_IMPRVMNT_IND] CHAR(1)  NULL ,
	[LOAN_PRPS_INCM_IND] CHAR(1)  NULL ,
	[LOAN_PRPS_INSRNC_IND] CHAR(1)  NULL ,
	[LOAN_PRPS_LEISURE_IND] CHAR(1)  NULL ,
	[LOAN_PRPS_MEDCL_IND] CHAR(1)  NULL ,
	[LOAN_PRPS_OTHR_IND] CHAR(1)  NULL ,
	[PROP_TYP]           varchar(16)  NULL ,
	[LOAN_PRPS_TAXES_IND] CHAR(1)  NULL ,
	[LOAN_PRPS_TEXT]     varchar(100)  NULL ,
	[LTV_CAT]            varchar(16)  NULL ,
	[LTV_CAT_NEW]        varchar(16)  NULL ,
	[LTV_CAT_OLD]        varchar(16)  NULL ,
	[RATIO_LOAN_TO_VL_NEW] numeric(7,2)  NULL ,
	[RATIO_LOAN_TO_VL_OLD] numeric(7,2)  NULL ,
	[LOAN_TYPE]          varchar(16)  NULL ,
	[LOSSMIT_CD]         varchar(16)  NULL ,
	[MNDTRY_OBLGTNS_AMT] INTEGER  NULL ,
	[MANDATORY_OBLIGS_AMT] INTEGER  NULL ,
	[MANUF_HSNG_IND]     CHAR(1)  NULL ,
	[MARRIED_TO_NBS_IND] CHAR(1)  NULL ,
	[CURR_MNTHLY_MIP]    numeric(7,2)  NULL ,
	[MISC_AUS_DCSN_CD]   varchar(16)  NULL ,
	[MISC_AUS_IND]       CHAR(3)  NULL ,
	[RANDOM_NUMBER]      numeric(3)  NULL ,
	[ME_DEBT_AMT]        INTEGER  NULL ,
	[ME_OTHER_AMT]       INTEGER  NULL ,
	[ME_RE_AMT]          INTEGER  NULL ,
	[ME_TOTAL_AMT]       INTEGER  NULL ,
	[MI_IMPUTED_AMT]     INTEGER  NULL ,
	[MI_OTHER_AMT]       INTEGER  NULL ,
	[MI_TOTAL_AMT]       INTEGER  NULL ,
	[MNTHLY_SET_ASIDE]   INTEGER  NULL ,
	[MORT_EXCLD_FNCD_MIP] numeric(9,2)  NULL ,
	[MIP_FINANCED_IND]   CHAR(1)  NULL ,
	[NBR_MONTHS]         numeric(3)  NULL ,
	[NBS_FIRST_MIDDLE_LAST] varchar(150)  NULL ,
	[NBRHD_PCT_OWNED]    INTEGER  NULL ,
	[NBRHD_PRICE]        INTEGER  NULL ,
	[MAX_CLAIM_AMT_NEW]  INTEGER  NULL ,
	[NOR_ISS_RPT_DT]     datetime  NULL ,
	[NBR_BTHRMS]         numeric(3,1)  NULL ,
	[NBR_BDRM]           numeric(3)  NULL ,
	[NBR_RMS]            numeric(3)  NULL ,
	[LIV_UNITS]          numeric(3)  NULL ,
	[OCPNCY_STS]         varchar(16)  NULL ,
	[OCPNCY_STS_CD]      varchar(16)  NULL ,
	[OCPNCY_STS_DT]      datetime  NULL ,
	[MAX_CLAIM_AMT_OLD]  INTEGER  NULL ,
	[OLDST_UNPD_DT]      datetime  NULL ,
	[ORIG_MRTG_AMT]      numeric(9,2)  NULL , 
	[ORGNTNG_MTGEE5]     CHAR(5)  NULL ,
	[ORGNTNG_MTGEE10_ID] CHAR(10)  NULL ,
	[TYP_ORGNTNG_MTGEE]  varchar(16)  NULL ,
	[OTHER_DEBT_CURR_IND] CHAR(1)  NULL ,
	[OTHER_DEBT_LATE_PMT_IND] CHAR(1)  NULL ,
	[PST_RVW_DCSN_CD]    varchar(16)  NULL ,
	[PRE_RVW_DCSN]       varchar(16)  NULL ,
	[PRE_CLSNG_IND]      CHAR(3)  NULL ,
	[PREQUAL_OUTPUT]     varchar(16)  NULL ,
	[PREV_COMPLT_SBDVSN_IND] CHAR(1)  NULL ,
	[PRNCPL_RDCTN_AMT]   numeric(9,2)   NULL ,
	[PRIOR_SALE_RQRD_IND] CHAR(1)  NULL ,
	[PRCSNG_TYP]         varchar(16)  NULL ,
	[PRODUCT_TYPE]       varchar(16)  NULL ,
	[PROG_ID_F17]        varchar(16)  NULL ,
	[DT_ACQ]             datetime  NULL ,
	[PROP_ADDR_ST]       varchar(10)  NULL ,
	[PRPRTY_APRSL_VL]    numeric(9,2)   NULL ,
	[PC_CONDO_FEE_AMT]   INTEGER  NULL ,
	[PC_FLOOD_INS_AMT]   INTEGER  NULL ,
	[PC_HAZ_INS_AMT]     INTEGER  NULL ,
	[PC_OTHER_AMT]       INTEGER  NULL ,
	[PC_RE_TAX_AMT]      INTEGER  NULL ,
	[PC_SETASIDE_TOT_AMT] INTEGER  NULL ,
	[PC_TOTAL_AMT]       INTEGER  NULL ,
	[PRPRTY_CNVRSN_TYP]  varchar(16)  NULL ,
	[PD_STRMLN_FLG]      CHAR(3)  NULL ,
	[PYMTS_BFR_FRST_MISSED_PYMT] numeric(3)  NULL ,
	[RATIO_ORE_TEI]      numeric(5,2)   NULL ,
	[RATIO_FIX_TEI]      numeric(5,2)   NULL ,
	[PTI_CAT]            char(5)  NULL ,
	[RATIO_TMP_TEI]      numeric(7,2)   NULL ,
	[RE_DEBT_CURR_IND]   CHAR(1)  NULL ,
	[RE_DEBT_LATE_PMT_IND] CHAR(1)  NULL ,
	[RE_TAX_CURR_IND]    CHAR(1)  NULL ,
	[RE_TAX_DELINQ_IND]  CHAR(1)  NULL ,
	[RFNC_CD]            varchar(16)  NULL ,
	[RFNC_IND]           CHAR(3)  NULL ,
	[RFNC_NEXT_CASE_NBR] char(11)  NULL ,
	[RPR_SET_ASIDE]      INTEGER  NULL ,
	[RQRD_INVEST]        numeric(9,2)   NULL ,
	[RI_FAMILY_SIZE]     numeric(3)  NULL ,
	[RI_SHORTFALL_AMT]   INTEGER  NULL ,
	[RI_STANDARD_AMT]    INTEGER  NULL ,
	[RI_TOTAL_AMT]       INTEGER  NULL ,
	[REVOLVE_DEBT_CURR_IND] CHAR(1)  NULL ,
	[REVOLVE_DEBT_LATE_PMT_IND] CHAR(1)  NULL ,
	[NBRHD_CD]           varchar(16)  NULL ,
	[RCV_SALE_DT]        datetime  NULL ,
	[SCNDRY_FNC_SRC]     varchar(16)  NULL ,
	[SECONDARY_FINANCE_AMT] INTEGER  NULL ,
	[SELLER_CNTRBTN]     numeric(9,2)   NULL ,
	[SEND_MIC_IND]       CHAR(1)  NULL ,
	[OLD_SRVCR_MTGEE]    CHAR(5)  NULL ,
	[SRVCR_MTGEE10_A43C] CHAR(10)  NULL ,
	[SRVCR_MTGEE5_A43]   CHAR(5)  NULL ,
	[SFPCS_MTGEE_ID]     CHAR(5)  NULL ,
	[SNGL_DSBRSE_LMP_SUM_PMT_OPT] INTEGER  NULL ,
	[PCT_1_FMLY]         INTEGER  NULL ,
	[SOA_CD]             varchar(16)  NULL ,
	[SPNSR_MTGEE10_ID]   CHAR(10)  NULL ,
	[SQNC_NBR]           INTEGER  NULL ,
	[STRMNLN_REFI_TYPE]  CHAR(1)  NULL ,
	[STRT_STOP_EPS_IND]  CHAR(2)  NULL ,
	[SBDVSN_SPOT_LOT]    varchar(16)  NULL ,
	[TAX_INSUR_YR_1_PMTS] INTEGER  NULL ,
	[TAXES_INSRNC_FRST_YR_AMT] INTEGER  NULL ,
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
	[TRNSMSN_TYP]        varchar(16)  NULL ,
	[UFMIP_PD_AMT]       numeric(9,2)   NULL ,
	[UFMIP_PD_CASH]      numeric(9,2)   NULL ,
	[UFMIP_EARNED_CURR_MM] numeric(9,2)   NULL ,
	[UFMIP_FACTOR]       numeric(7,5)   NULL ,
	[UNDERWRITER_ID]     varchar(16)  NULL ,
	[UNDRWRTING_MTGEE5]  CHAR(5)  NULL ,
	[UNPD_BAL]           INTEGER  NULL ,
	[VAL_PLUS_CLSNG]     INTEGER  NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [LOAN_SELECTION_CASE_SUMMARY]
	ADD CONSTRAINT [LOAN_SELECTION_CASE_SUMMARY_PK] PRIMARY KEY  NONCLUSTERED ([CASE_NUMBER] ASC,[SELECTION_ID] ASC)
go

CREATE TABLE [PERSONNEL]
( 
	[PRSNNL_ID]          varchar(32)  NOT NULL ,
	[EMAIL_ADDRESS]      varchar(255)  NULL ,
	[RVW_LOCATION_ID]    varchar(16)  NULL ,
	[REPORTS_TO_PRSNNL_ID] varchar(32)  NULL ,
	[FIRST_NAME]         varchar(100)  NULL ,
	[LAST_NAME]          varchar(100)  NULL ,
	[VETTING_PRSNNL_ID]  varchar(32)  NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [PERSONNEL]
	ADD CONSTRAINT [PERSONNEL_PK] PRIMARY KEY  NONCLUSTERED ([PRSNNL_ID] ASC)
go

CREATE TABLE [PERSONNEL_ASSIGNMENT_TYPE]
( 
	[PRSNNL_ID]          varchar(32)  NOT NULL ,
	[ASSIGNMENT_TYPE_CD] varchar(16)  NOT NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [PERSONNEL_ASSIGNMENT_TYPE]
	ADD CONSTRAINT [PERSONNEL_ASSIGNMENT_TYPE_PK] PRIMARY KEY  NONCLUSTERED ([PRSNNL_ID] ASC,[ASSIGNMENT_TYPE_CD] ASC)
go

CREATE TABLE [PROC_UNIVERSE]
( 
	[CASE_AS_OF_DT]      datetime  NULL ,
	[CASE_COUNT]         INTEGER  NULL ,
	[UNIVERSE_ID]        varchar(30)  NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

CREATE TABLE [QATREE]
( 
	[QATREE_ID]          varchar(16)  NOT NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL ,
	[DEFECT_CD]          varchar(16)  NULL 
)
go

ALTER TABLE [QATREE]
	ADD CONSTRAINT [QATREE_PK] PRIMARY KEY  NONCLUSTERED ([QATREE_ID] ASC)
go

CREATE TABLE [QATREE_BRANCH]
( 
	[QUESTION_ID]        varchar(16)  NOT NULL ,
	[RESPONSE]           varchar(16)  NOT NULL ,
	[QATREE_ID]          varchar(16)  NOT NULL ,
	[NEXT_QUESTION_ID]   varchar(16)  NULL ,
	[QA_OUTCM_ID]        varchar(16)  NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [QATREE_BRANCH]
	ADD CONSTRAINT [QATREE_BRANCH_PK] PRIMARY KEY  NONCLUSTERED ([QUESTION_ID] ASC,[RESPONSE] ASC,[QATREE_ID] ASC)
go

CREATE TABLE [QATREE_OUTCOME]
( 
	[QA_OUTCM_ID]        varchar(16)  NOT NULL ,
	[DEFECT_CD]          varchar(16)  NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [QATREE_OUTCOME]
	ADD CONSTRAINT [QATREE_OUTCOME_PK] PRIMARY KEY  NONCLUSTERED ([QA_OUTCM_ID] ASC)
go

CREATE TABLE [QATREE_OUTCM_TIERS]
( 
	[SEVERITY_TIER_CD]   varchar(16)  NOT NULL ,
	[QA_OUTCM_ID]        varchar(16)  NOT NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [QATREE_OUTCM_TIERS]
	ADD CONSTRAINT [QATREE_OUTCM_TIERS_PK] PRIMARY KEY  NONCLUSTERED ([SEVERITY_TIER_CD] ASC,[QA_OUTCM_ID] ASC)
go

CREATE TABLE [QATREE_OUTCM_SOURCES]
( 
	[DFCT_SOURCE_CD]     varchar(16)  NOT NULL ,
	[QA_OUTCM_ID]        varchar(16)  NOT NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [QATREE_OUTCM_SOURCES]
	ADD CONSTRAINT [QATREE_OUTCM_SOURCES_PK] PRIMARY KEY  NONCLUSTERED ([DFCT_SOURCE_CD] ASC,[QA_OUTCM_ID] ASC)
go

CREATE TABLE [QATREE_OUTCM_CAUSES]
( 
	[DEFECT_CAUSE_CD]    varchar(16)  NOT NULL ,
	[QA_OUTCM_ID]        varchar(16)  NOT NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [QATREE_OUTCM_CAUSES]
	ADD CONSTRAINT [QATREE_OUTCM_CAUSES_PK] PRIMARY KEY  NONCLUSTERED ([DEFECT_CAUSE_CD] ASC,[QA_OUTCM_ID] ASC)
go

CREATE TABLE [QATREE_QUESTION]
( 
	[QUESTION_ID]        varchar(16)  NOT NULL ,
	[QATREE_ID]          varchar(16)  NOT NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [QATREE_QUESTION]
	ADD CONSTRAINT [QATREE_QUESTION_PK] PRIMARY KEY  NONCLUSTERED ([QUESTION_ID] ASC,[QATREE_ID] ASC)
go

CREATE TABLE [QATREE_QSTN_CONDITION]
( 
	[ENTITY_NAME]        character varying(60)  NOT NULL ,
	[FIELD_NAME]         character varying(60)  NOT NULL ,
	[QUESTION_ID]        varchar(16)  NOT NULL ,
	[QATREE_ID]          varchar(16)  NOT NULL ,
	[COMPARISON_VALUES]  varchar(200)  NULL ,
	[CONDITION_OPERATOR] varchar(16)  NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [QATREE_QSTN_CONDITION]
	ADD CONSTRAINT [QATREE_QSTN_COND_PK] PRIMARY KEY  NONCLUSTERED ([QATREE_ID] ASC,[QUESTION_ID] ASC,[ENTITY_NAME] ASC,[FIELD_NAME] ASC)
go

CREATE TABLE [QATREE_QSTN_CAPTURE]
( 
	[ENTITY_NAME]        character varying(60)  NOT NULL ,
	[FIELD_NAME]         character varying(60)  NOT NULL ,
	[QUESTION_ID]        varchar(16)  NOT NULL ,
	[QATREE_ID]          varchar(16)  NOT NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [QATREE_QSTN_CAPTURE]
	ADD CONSTRAINT [QATREE_QSTN_CAPT_PK] PRIMARY KEY  NONCLUSTERED ([QATREE_ID] ASC,[QUESTION_ID] ASC,[ENTITY_NAME] ASC,[FIELD_NAME] ASC)
go

CREATE TABLE [QATREE_USAGE_RULES]
( 
	[DEFECT_CD]          varchar(16)  NOT NULL ,
	[QATREE_ID]          varchar(16)  NOT NULL ,
	[USAGE_RULE_TYPE]    varchar(16)  NOT NULL ,
	[ASSIGNMENT_DATE_END] datetime  NULL ,
	[ASSIGNMENT_DATE_START] datetime  NULL ,
	[ENDORSE_DATE_END]   datetime  NULL ,
	[ENDORSE_DATE_START] datetime  NULL ,
	[SELECTION_DATE_END] datetime  NULL ,
	[SELECTION_DATE_START] datetime  NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [QATREE_USAGE_RULES]
	ADD CONSTRAINT [QATREE_USAGE_RULES_PK] PRIMARY KEY  NONCLUSTERED ([DEFECT_CD] ASC,[QATREE_ID] ASC,[USAGE_RULE_TYPE] ASC)
go

CREATE TABLE [REQUESTED_BINDER]
( 
	[SLCTN_REQ_ID]       CHAR(36)  NOT NULL ,
	[BATCH_ID]           varchar(16)  NULL ,
	[CASE_NUMBER]        char(11)  NULL ,
	[REQUESTED_DATE]     datetime  NULL ,
	[DUE_DATE]           datetime  NULL ,
	[STATUS]             varchar(16)  NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [REQUESTED_BINDER]
	ADD CONSTRAINT [REQUESTED_BINDER_PK] PRIMARY KEY  NONCLUSTERED ([SLCTN_REQ_ID] ASC)
go

CREATE TABLE [REVIEW]
( 
	[REVIEW_ID]          varchar(14)  NOT NULL ,
	[BATCH_ID]           varchar(16)  NULL ,
	[CASE_NUMBER]        char(11)  NULL ,
	[REVIEW_LEVEL_CD]    varchar(16)  NULL ,
	[ORIG_MIT_RQST_DT]   datetime  NULL ,
	[ORIG_ASSIGN_DT]     datetime  NULL ,
	[SELECTION_ID]       int  NULL ,
	[ORIG_RVWR_PRSNNL_ID] varchar(32)  NULL ,
	[RVW_COMPLTD_DT]     datetime  NULL ,
	[RVW_STARTED_DT_TM]  datetime  NULL ,
	[STATUS]             varchar(16)  NULL ,
	[SUMMARY_RATING_CD]  varchar(16)  NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [REVIEW]
	ADD CONSTRAINT [REVIEW_PK] PRIMARY KEY  NONCLUSTERED ([REVIEW_ID] ASC)
go

CREATE TABLE [REVIEW_LEVEL]
( 
	[REVIEW_LVL_ID]      CHAR(36)  NOT NULL ,
	[ASSIGN_DT]          datetime  NULL ,
	[COMPLT_DT]          datetime  NULL ,
	[INDEM_DATE]         datetime  NULL ,
	[INDEM_TYPE]         varchar(16)  NULL ,
	[ITERATION_NUM]      numeric(3)  NULL ,
	[NOTES]              text  NULL ,
	[ORIG_RVWR_PRSNNL_ID] varchar(32)  NULL ,
	[PARENT_REVIEW_ID]   varchar(14)  NULL ,
	[REVIEW_ID]          varchar(14)  NULL ,
	[REVIEW_LEVEL_CD]    varchar(16)  NULL ,
	[STATUS]             varchar(16)  NULL ,
	[RVW_LOCATION_ID]    varchar(16)  NULL ,
	[RVW_STARTED_DT_TM]  datetime  NULL ,
	[RVWR_PRSNNL_ID]     varchar(32)  NULL ,
	[RATING_CD]          varchar(16)  NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [REVIEW_LEVEL]
	ADD CONSTRAINT [REVIEW_LEVEL_PK] PRIMARY KEY  NONCLUSTERED ([REVIEW_LVL_ID] ASC)
go

CREATE INDEX [RVW_LVL_RVW_TYPE_ITERATION_IDX] ON [REVIEW_LEVEL]
( 
	[REVIEW_ID]           ASC,
	[REVIEW_LEVEL_CD]     ASC,
	[ITERATION_NUM]       ASC
)
go

CREATE TABLE [REVIEW_LOCATION]
( 
	[RVW_LOCATION_ID]    varchar(16)  NOT NULL ,
	[CONTACT_NAME]       varchar(100)  NULL ,
	[CONTACT_PHONE_NUMBER] varchar(32)  NULL ,
	[MAILING_ADDRESS]    varchar(100)  NULL ,
	[MAILING_CITY]       varchar(100)  NULL ,
	[MAILING_STATE]      varchar(16)  NULL ,
	[MAILING_ZIP]        varchar(16)  NULL ,
	[COMMITTED_MONTHLY_CAPACITY] INTEGER  NULL ,
	[OWNER_PRSNNL_ID]    varchar(32)  NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [REVIEW_LOCATION]
	ADD CONSTRAINT [REVIEW_LOCATION_PK] PRIMARY KEY  NONCLUSTERED ([RVW_LOCATION_ID] ASC)
go

CREATE TABLE [REVIEW_TYPE_DEFECT]
( 
	[REVIEW_TYPE]        varchar(16)  NOT NULL ,
	[DEFECT_CD]          varchar(16)  NOT NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [REVIEW_TYPE_DEFECT]
	ADD CONSTRAINT [REVIEW_TYPE_DEFECT_PK] PRIMARY KEY  NONCLUSTERED ([REVIEW_TYPE] ASC,[DEFECT_CD] ASC)
go

CREATE TABLE [RVW_LVL_AUDIT]
( 
	[AUDIT_TMSTMP]       datetime  NOT NULL ,
	[REVIEW_LVL_ID]      CHAR(36)  NOT NULL ,
	[INDEM_DATE]         datetime  NULL ,
	[INDEM_TYPE]         varchar(16)  NULL ,
	[NOTES]              text  NULL ,
	[RVWR_PRSNNL_ID]     varchar(32)  NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [RVW_LVL_AUDIT]
	ADD CONSTRAINT [RVW_LVL_AUDIT_PK] PRIMARY KEY  NONCLUSTERED ([AUDIT_TMSTMP] ASC,[REVIEW_LVL_ID] ASC)
go

CREATE TABLE [RVW_LVL_FINDING]
( 
	[REVIEW_LVL_ID]      CHAR(36)  NOT NULL ,
	[DEFECT_CD]          varchar(16)  NOT NULL ,
	[DFCT_SOURCE_CD]     varchar(16)  NOT NULL ,
	[DEFECT_CAUSE_CD]    varchar(16)  NOT NULL ,
	[SEVERITY_TIER_CD]   varchar(16)  NULL ,
	[QUESTION_ID]        varchar(16)  NULL ,
	[QATREE_ID]          varchar(16)  NULL ,
	[REMEDY_TYPE]        varchar(16)  NULL ,
	[NOTES]              text  NULL ,
	[RECISSION_IND]      CHAR(1)  NULL ,
	[REMEDIED_DT]        datetime  NULL ,
	[REMEDY_AMOUNT]      INTEGER  NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [RVW_LVL_FINDING]
	ADD CONSTRAINT [RVW_LVL_FINDING_PK] PRIMARY KEY  NONCLUSTERED ([REVIEW_LVL_ID] ASC,[DEFECT_CD] ASC,[DFCT_SOURCE_CD] ASC,[DEFECT_CAUSE_CD] ASC)
go

CREATE TABLE [RVW_LVL_FINDING_AUD]
( 
	[REVIEW_LVL_ID]      CHAR(36)  NOT NULL ,
	[DEFECT_CD]          character varying(16)  NOT NULL ,
	[DFCT_SOURCE_CD]     varchar(16)  NOT NULL ,
	[DEFECT_CAUSE_CD]    varchar(16)  NOT NULL ,
	[AUDIT_TMSTMP]       datetime  NOT NULL ,
	[NOTES]              text  NULL ,
	[SEVERITY_TIER_CD]   varchar(16)  NULL ,
	[QUESTION_ID]        varchar(16)  NULL ,
	[QATREE_ID]          varchar(16)  NULL ,
	[RECISSION_IND]      CHAR(1)  NULL ,
	[REMEDIED_DT]        datetime  NULL ,
	[REMEDY_AMOUNT]      INTEGER  NULL ,
	[REMEDY_TYPE]        varchar(16)  NULL ,
	[RVWR_PRSNNL_ID]     varchar(32)  NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [RVW_LVL_FINDING_AUD]
	ADD CONSTRAINT [RVW_LVL_FINDING_AUD_PK] PRIMARY KEY  NONCLUSTERED ([REVIEW_LVL_ID] ASC,[DEFECT_CD] ASC,[DFCT_SOURCE_CD] ASC,[DEFECT_CAUSE_CD] ASC,[AUDIT_TMSTMP] ASC)
go

CREATE TABLE [RVW_LVL_RFRRL]
( 
	[NOTES]              text  NULL ,
	[REFERRAL_TYPE]      varchar(16)  NOT NULL ,
	[RFRRL_TMSTMP]       datetime  NULL ,
	[REVIEW_LVL_ID]      CHAR(36)  NOT NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [RVW_LVL_RFRRL]
	ADD CONSTRAINT [RVW_LVL_RFRRL_PK] PRIMARY KEY  NONCLUSTERED ([REVIEW_LVL_ID] ASC,[REFERRAL_TYPE] ASC)
go

CREATE TABLE [RVW_LVL_RFRRL_AUD]
( 
	[AUDIT_TMSTMP]       datetime  NOT NULL ,
	[REFERRAL_TYPE]      varchar(16)  NOT NULL ,
	[REVIEW_LVL_ID]      CHAR(36)  NOT NULL ,
	[NOTES]              text  NULL ,
	[RFRRL_TMSTMP]       datetime  NULL ,
	[RVWR_PRSNNL_ID]     varchar(32)  NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [RVW_LVL_RFRRL_AUD]
	ADD CONSTRAINT [RVW_LVL_RFRRL_AUD_PK] PRIMARY KEY  NONCLUSTERED ([AUDIT_TMSTMP] ASC,[REFERRAL_TYPE] ASC,[REVIEW_LVL_ID] ASC)
go

CREATE TABLE [RVW_DEFECT]
( 
	[REVIEW_ID]          varchar(14)  NOT NULL ,
	[DEFECT_CD]          varchar(16)  NOT NULL ,
	[SUMMARY_RATING_CD]  varchar(16)  NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [RVW_DEFECT]
	ADD CONSTRAINT [RVW_DEFECT_PK] PRIMARY KEY  NONCLUSTERED ([REVIEW_ID] ASC,[DEFECT_CD] ASC)
go

CREATE TABLE [RQST_FROM_LENDER]
( 
	[REQUEST_ID]         CHAR(36)  NOT NULL ,
	[SLCTN_REQ_ID]       CHAR(36)  NOT NULL ,
	[CASE_NUMBER]        char(11)  NULL ,
	[REQUESTED_DATE]     datetime  NULL ,
	[DUE_DATE]           datetime  NULL ,
	[REQUEST_TYPE]       varchar(16)  NULL ,
	[REVIEW_ID]          varchar(14)  NULL ,
	[STATUS]             varchar(16)  NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [RQST_FROM_LENDER]
	ADD CONSTRAINT [RQST_FROM_LENDER_PK] PRIMARY KEY  NONCLUSTERED ([REQUEST_ID] ASC,[SLCTN_REQ_ID] ASC)
go

CREATE TABLE [RVW_BATCH]
( 
	[BATCH_ID]           varchar(16)  NOT NULL ,
	[OWNER_PRSNNL_ID]    varchar(32)  NULL ,
	[STATUS]             varchar(16)  NULL ,
	[MTGEE5]             CHAR(5)  NULL ,
	[ORIG_OWNR_PRSNNL_ID] varchar(32)  NULL ,
	[SLCTN_REQ_ID]       CHAR(36)  NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [RVW_BATCH]
	ADD CONSTRAINT [RVW_BATCH_PK] PRIMARY KEY  NONCLUSTERED ([BATCH_ID] ASC)
go

CREATE TABLE [RVW_BATCH_PRSNNL]
( 
	[BATCH_ID]           varchar(16)  NOT NULL ,
	[PRSNNL_ID]          varchar(32)  NOT NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [RVW_BATCH_PRSNNL]
	ADD CONSTRAINT [RVW_BATCH_PRSNNL_PK] PRIMARY KEY  NONCLUSTERED ([BATCH_ID] ASC,[PRSNNL_ID] ASC)
go

CREATE TABLE [REVIEW_LEVEL_ADMIN]
( 
	[REVIEW_LEVEL_CD]    varchar(16)  NOT NULL ,
	[ASSIGNMENT_TYPE_CD] varchar(16)  NULL ,
	[FORCE_ESCALATE_ALLOWED] CHAR(1)  NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [REVIEW_LEVEL_ADMIN]
	ADD CONSTRAINT [REVIEW_LEVEL_ADMIN_PK] PRIMARY KEY  NONCLUSTERED ([REVIEW_LEVEL_CD] ASC)
go

CREATE TABLE [RVW_LVL_QSTN_RESPONSE]
( 
	[REVIEW_LVL_ID]      CHAR(36)  NOT NULL ,
	[QATREE_ID]          varchar(16)  NOT NULL ,
	[QUESTION_ID]        varchar(16)  NOT NULL ,
	[RESPONSE]           varchar(16)  NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [RVW_LVL_QSTN_RESPONSE]
	ADD CONSTRAINT [RVW_LVL_QSTN_RESPONSE_PK] PRIMARY KEY  NONCLUSTERED ([REVIEW_LVL_ID] ASC,[QATREE_ID] ASC,[QUESTION_ID] ASC)
go

CREATE TABLE [SELECT_REQUEST_LENDER_MON]
( 
	[SLCTN_REQ_ID]       CHAR(36)  NOT NULL ,
	[CASE_COUNT]         INTEGER  NULL ,
	[REVIEW_TYPE]        varchar(16)  NULL ,
	[MORTGAGEE_BATCH_REFERENCE] varchar(100)  NULL ,
	[PREF_RVW_LOCATION]  varchar(16)  NULL ,
	[MODEL_ID]           varchar(30)  NULL ,
	[REQUESTED_DT_TM]    datetime  NULL ,
	[RQST_LOAN_DOCS_SOURCE_CD] varchar(16)  NULL ,
	[RQST_OPR_DOCS_SOURCE_CD] varchar(16)  NULL ,
	[REQUEST_OPS_DOCS_IND] CHAR(1)  NULL ,
	[ENDORSE_RANGE_END_DT] datetime  NULL ,
	[ENDORSE_RANGE_START_DT] datetime  NULL ,
	[MTGEE5]             CHAR(5)  NULL ,
	[LOAN_TYPE]          varchar(16)  NULL ,
	[RQSTR_PRSNNL_ID]    varchar(32)  NULL ,
	[SECONDARY_REFERENCE] varchar(100)  NULL ,
	[TARGETED_RVW_DT]    datetime  NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [SELECT_REQUEST_LENDER_MON]
	ADD CONSTRAINT [SELECT_REQUEST_LENDER_MON_PK] PRIMARY KEY  NONCLUSTERED ([SLCTN_REQ_ID] ASC)
go

CREATE TABLE [SELECT_REQUEST_MANUAL_FHA]
( 
	[SLCTN_REQ_ID]       CHAR(36)  NOT NULL ,
	[REQUESTED_DT_TM]    datetime  NULL ,
	[RQST_LOAN_DOCS_SOURCE_CD] varchar(16)  NULL ,
	[CASE_NUMBER]        char(11)  NULL ,
	[RQSTR_PRSNNL_ID]    varchar(32)  NULL ,
	[REVIEW_TYPE]        varchar(16)  NULL ,
	[SECONDARY_REFERENCE] varchar(100)  NULL ,
	[SLCTN_REASON_ID]    varchar(16)  NULL ,
	[SELECTION_SUB_REASON] varchar(16)  NULL ,
	[TARGETED_RVW_DT]    datetime  NULL ,
	[RVW_LOCATION_ID]    varchar(16)  NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [SELECT_REQUEST_MANUAL_FHA]
	ADD CONSTRAINT [SELECT_REQUEST_MANUAL_FHA_PK] PRIMARY KEY  NONCLUSTERED ([SLCTN_REQ_ID] ASC)
go

CREATE TABLE [SELECTION_PARAMETERS]
( 
	[SLCTN_REASON_ID]    varchar(16)  NOT NULL ,
	[BATCHES_ALLOWED_IND] CHAR(1)  NULL ,
	[CAPACITY_HANDLING_RULE] varchar(16)  NULL ,
	[MIN_QUANTITY]       INTEGER  NULL ,
	[MODEL_ID]           varchar(30)  NULL ,
	[MODEL_SCORE_THRESHOLD] INTEGER  NULL ,
	[PRIORITY]           numeric(3)  NULL ,
	[PROCESSING_CYCLE]   varchar(16)  NULL ,
	[UNIVERSE_ID]        varchar(30)  NULL ,
	[REQUIRE_FULL_QATREE_IND] CHAR(1)  NULL ,
	[REVIEW_TYPE]        varchar(16)  NULL ,
	[SAMPLING_METHOD]    varchar(16)  NULL ,
	[SAMPLING_PERCENTAGE] numeric(3)  NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [SELECTION_PARAMETERS]
	ADD CONSTRAINT [SELECTION_PARAMETERS_PK] PRIMARY KEY  NONCLUSTERED ([SLCTN_REASON_ID] ASC)
go

CREATE TABLE [QATREE_QSTN_RULES]
( 
	[REVIEW_TYPE]        varchar(16)  NOT NULL ,
	[SLCTN_REASON_ID]    varchar(16)  NOT NULL ,
	[QATREE_ID]          varchar(16)  NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [QATREE_QSTN_RULES]
	ADD CONSTRAINT [QATREE_QSTN_RULES_PK] PRIMARY KEY  NONCLUSTERED ([REVIEW_TYPE] ASC,[SLCTN_REASON_ID] ASC)
go

CREATE TABLE [LOAN_SELECTION_PENDING]
( 
	[CASE_NUMBER]        char(11)  NOT NULL ,
	[SELECTION_DT]       datetime  NOT NULL ,
	[SLCTN_REASON_ID]    varchar(16)  NOT NULL ,
	[MODEL_ID]           varchar(30)  NULL ,
	[MODEL_SCORE]        INTEGER  NULL ,
	[REVIEW_TYPE]        varchar(16)  NULL ,
	[SELECTION_SUB_REASON] varchar(16)  NULL ,
	[RVW_LOCATION_ID]    varchar(16)  NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [LOAN_SELECTION_PENDING]
	ADD CONSTRAINT [LOAN_SELECTION_PENDING_PK] PRIMARY KEY  NONCLUSTERED ([CASE_NUMBER] ASC,[SELECTION_DT] ASC,[SLCTN_REASON_ID] ASC)
go

CREATE TABLE [DELIV_PARMS_REQUEST]
( 
	[INTERATION_NUM]     numeric(3)  NOT NULL ,
	[REVIEW_LEVEL_CD]    varchar(16)  NOT NULL ,
	[SLCTN_REASON_ID]    varchar(16)  NOT NULL ,
	[ESCALATION_ACTION]  varchar(16)  NULL ,
	[DAYS_TO_SATISFY]    numeric(3)  NULL ,
	[ESCALATION_REVIEW_LEVEL] varchar(16)  NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [DELIV_PARMS_REQUEST]
	ADD CONSTRAINT [DELIV_PARMS_REQUEST_PK] PRIMARY KEY  NONCLUSTERED ([INTERATION_NUM] ASC,[REVIEW_LEVEL_CD] ASC,[SLCTN_REASON_ID] ASC)
go

CREATE TABLE [DELIV_PARMS_ITERATION]
( 
	[REVIEW_LEVEL_CD]    varchar(16)  NOT NULL ,
	[SLCTN_REASON_ID]    varchar(16)  NOT NULL ,
	[NUMBER_OF_ITERATIONS] numeric(3)  NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [DELIV_PARMS_ITERATION]
	ADD CONSTRAINT [DELIV_PARMS_ITERATION_PK] PRIMARY KEY  NONCLUSTERED ([REVIEW_LEVEL_CD] ASC,[SLCTN_REASON_ID] ASC)
go

CREATE TABLE [RVW_LOCATION_REASON]
( 
	[RVW_LOCATION_ID]    varchar(16)  NOT NULL ,
	[SELECTION_REASON]   varchar(16)  NOT NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [RVW_LOCATION_REASON]
	ADD CONSTRAINT [RVW_LOCATION_REASON_PK] PRIMARY KEY  NONCLUSTERED ([RVW_LOCATION_ID] ASC,[SELECTION_REASON] ASC)
go

CREATE TABLE [RVW_LVL_CASE_SUMMARY]
( 
	[REVIEW_ID]          varchar(14)  NOT NULL ,
	[ASSUMED_LOAN_IND]   CHAR(1)  NULL ,
	[CASHOUT_REFI_IND]   CHAR(1)  NULL ,
	[DECLINING_MKT_IND]  CHAR(1)  NULL ,
	[DISASTER_IND]       CHAR(1)  NULL ,
	[GIFT_LTR_AMT]       INTEGER  NULL ,
	[GIFT_LTR_2_AMT]     INTEGER  NULL ,
	[GIFT_LTR_SRC]       varchar(32)  NULL ,
	[GIFT_LTR_2_SOURCE]  varchar(32)  NULL ,
	[INVEST_2ND_RESID_IND] CHAR(1)  NULL ,
	[NUM_LIVING_UNITS]   numeric(3)  NULL ,
	[LOAN_PRPS]          varchar(3)  NULL ,
	[RATIO_LOAN_TO_VL_NEW] numeric(7,2)  NULL ,
	[LOAN_TYPE]          varchar(16)  NULL ,
	[FCTRY_FBRCT]        CHAR(1)  NULL ,
	[MARKET_CONDITION_RPT] CHAR(1)  NULL ,
	[MAX_CLAIM_AMT]      INTEGER  NULL ,
	[MIXED_USE_PROP_IND] CHAR(1)  NULL ,
	[CURRENT_AT_ENDORSE_IND] CHAR(1)  NULL ,
	[NON_PUB_UTIL_COMMON_IND] CHAR(1)  NULL ,
	[PAYSTUBS_COVER_30_DAYS_IND] CHAR(1)  NULL ,
	[PRODUCT_TYPE]       varchar(16)  NULL ,
	[PROP_CURR_OCCUPANCY_TYPE] varchar(16)  NULL ,
	[PD_STRMLN_FLG]      CHAR(1)  NULL ,
	[PROPERTY_REPAIRS]   INTEGER  NULL ,
	[PUB_WELL_SEPTIC_AVAIL_AREA_IND] CHAR(1)  NULL ,
	[PUB_WELL_SEPTIC_AVAIL_SITE_IND] CHAR(1)  NULL ,
	[RATIO_TOT_PMT_TO_TOT_INC] INTEGER  NULL ,
	[RFNC_CD]            varchar(16)  NULL ,
	[REPAIR_ESCROW_ACCT_ESTAB] CHAR(1)  NULL ,
	[SLS_PRC_GT_ACQ_COST] INTEGER  NULL ,
	[SOA_CD]             varchar(16)  NULL ,
	[UNDERWRITING_METHOD] varchar(16)  NULL ,
	[UTIL_ON_AT_INSPECTION] CHAR(1)  NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [RVW_LVL_CASE_SUMMARY]
	ADD CONSTRAINT [RVW_LVL_CASE_SUMMARY_PK] PRIMARY KEY  NONCLUSTERED ([REVIEW_ID] ASC)
go

CREATE TABLE [RVW_LVL_DEFECT]
( 
	[REVIEW_LVL_ID]      CHAR(36)  NOT NULL ,
	[DEFECT_CD]          varchar(16)  NOT NULL ,
	[REVIEW_ID]          varchar(14)  NULL ,
	[COMMENTS]           text  NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [RVW_LVL_DEFECT]
	ADD CONSTRAINT [RVW_LVL_DEFECT_PK] PRIMARY KEY  NONCLUSTERED ([REVIEW_LVL_ID] ASC,[DEFECT_CD] ASC)
go

CREATE TABLE [RVW_LVL_DEFECT_AUD]
( 
	[REVIEW_LVL_ID]      CHAR(36)  NOT NULL ,
	[DEFECT_CD]          varchar(16)  NOT NULL ,
	[AUDIT_TMSTMP]       datetime  NOT NULL ,
	[REVIEW_ID]          varchar(14)  NULL ,
	[COMMENTS]           text  NULL ,
	[RVWR_PRSNNL_ID]     varchar(32)  NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [RVW_LVL_DEFECT_AUD]
	ADD CONSTRAINT [RVW_LVL_DEFECT_AUD_PK] PRIMARY KEY  NONCLUSTERED ([REVIEW_LVL_ID] ASC,[DEFECT_CD] ASC,[AUDIT_TMSTMP] ASC)
go

CREATE TABLE [RVW_LVL_INDEM]
( 
	[REVIEW_LVL_ID]      CHAR(36)  NOT NULL ,
	[INDEM_TYPE]         varchar(16)  NOT NULL ,
	[INDEM_DATE]         datetime  NULL ,
	[INDEM_STATUS]       varchar(16)  NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [RVW_LVL_INDEM]
	ADD CONSTRAINT [RVW_LVL_INDEM_PK] PRIMARY KEY  NONCLUSTERED ([REVIEW_LVL_ID] ASC,[INDEM_TYPE] ASC)
go

CREATE TABLE [RVW_LVL_INDEM_AUD]
( 
	[AUDIT_TMSTMP]       datetime  NOT NULL ,
	[INDEM_TYPE]         varchar(16)  NOT NULL ,
	[REVIEW_LVL_ID]      CHAR(36)  NOT NULL ,
	[INDEM_DATE]         datetime  NULL ,
	[INDEM_STATUS]       varchar(16)  NULL ,
	[RVWR_PRSNNL_ID]     varchar(32)  NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [RVW_LVL_INDEM_AUD]
	ADD CONSTRAINT [RVW_LVL_INDEM_AUD_PK] PRIMARY KEY  NONCLUSTERED ([REVIEW_LVL_ID] ASC,[INDEM_TYPE] ASC,[AUDIT_TMSTMP] ASC)
go

CREATE TABLE [SCORING_MODEL]
( 
	[MODEL_ID]           varchar(30)  NOT NULL ,
	[PRIORITY]           numeric(3)  NULL ,
	[RETAIN_AS_NON_PERF_IND] CHAR(1)  NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [SCORING_MODEL]
	ADD CONSTRAINT [SCORING_MODEL_PK] PRIMARY KEY  NONCLUSTERED ([MODEL_ID] ASC)
go

CREATE TABLE [SCORING_MODEL_FACTORS]
( 
	[FACTOR_ID]          varchar(16)  NOT NULL ,
	[MODEL_ID]           varchar(30)  NOT NULL ,
	[MODEL_VER_NUM]      numeric(3)  NOT NULL ,
	[FIELD_NAME]         varchar(60)  NULL ,
	[COMMENTS]           text  NULL ,
	[ENTITY_NAME]        varchar(60)  NULL ,
	[WEIGHT]             INTEGER  NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [SCORING_MODEL_FACTORS]
	ADD CONSTRAINT [SCORING_MODEL_FACTORS_PK] PRIMARY KEY  NONCLUSTERED ([FACTOR_ID] ASC,[MODEL_ID] ASC,[MODEL_VER_NUM] ASC)
go

CREATE TABLE [MODEL_SCORE]
( 
	[CASE_NUMBER]        char(11)  NOT NULL ,
	[MODEL_ID]           varchar(30)  NOT NULL ,
	[LENDER_SCORE_PCTILE] INTEGER  NULL ,
	[MODEL_SCORE]        INTEGER  NULL ,
	[UNIVERSE_ID]        varchar(30)  NULL ,
	[SCORE_CALCULATED_DT] datetime  NULL ,
	[SCORE_PCTILE]       INTEGER  NULL ,
	[SCORE_RANK]         INTEGER  NULL ,
	[UWRTR_SCORE_PCTILE] INTEGER  NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [MODEL_SCORE]
	ADD CONSTRAINT [MODEL_SCORE_PK] PRIMARY KEY  NONCLUSTERED ([CASE_NUMBER] ASC,[MODEL_ID] ASC)
go

CREATE TABLE [RVW_HISTORY]
( 
	[CASE_NUMBER]        char(11)  NOT NULL ,
	[WORST_RATING]       varchar(16)  NULL ,
	[LATEST_SVCNG_RVW_DT] datetime  NULL ,
	[LATEST_UWRTG_RVW_DT] datetime  NULL ,
	[QTY_SVCNG_RVWS]     INTEGER  NULL ,
	[QTY_UWRTNG_RVWS]    INTEGER  NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [RVW_HISTORY]
	ADD CONSTRAINT [RVW_HISTORY_PK] PRIMARY KEY  NONCLUSTERED ([CASE_NUMBER] ASC)
go

CREATE TABLE [SCORING_MODEL_VERSION]
( 
	[MODEL_ID]           varchar(30)  NOT NULL ,
	[MODEL_VER_NUM]      numeric(3)  NOT NULL ,
	[MODEL_SCORE_THRESHOLD] INTEGER  NULL ,
	[STATUS]             varchar(16)  NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [SCORING_MODEL_VERSION]
	ADD CONSTRAINT [SCORING_MODEL_VERSION_PK] PRIMARY KEY  NONCLUSTERED ([MODEL_ID] ASC,[MODEL_VER_NUM] ASC)
go

CREATE TABLE [SELECT_REQUEST_LENDER_SELF]
( 
	[SLCTN_REQ_ID]       CHAR(36)  NOT NULL ,
	[AGENCY_RFRRL_DT]    datetime  NULL ,
	[AGENCY_RFRRL_TYPE]  varchar(16)  NULL ,
	[BROKER_TPO_NAME]    varchar(100)  NULL ,
	[CASE_NUMBER]        char(11)  NULL ,
	[COMMENTS]           varchar(2000)  NULL ,
	[FRAUD_CORRECTIVE_ACTION] varchar(2000)  NULL ,
	[FRAUD_DETECTED_IND] CHAR(1)  NULL ,
	[FRAUD_PARTY_1]      varchar(100)  NULL ,
	[FRAUD_PARTY_1_OTHER] varchar(100)  NULL ,
	[FRAUD_PARTY_2]      varchar(100)  NULL ,
	[FRAUD_PARTY_2_OTHER] varchar(100)  NULL ,
	[SVCING_ISSUE_IND]   CHAR(1)  NULL ,
	[UWRTNG_ISSUE_IND]   CHAR(1)  NULL ,
	[MTGEE_LOAN_NUMBER]  varchar(32)  NULL ,
	[LOAN_OFFICER]       varchar(16)  NULL ,
	[MORTGAGEE_CONTACT_NAME] varchar(100)  NULL ,
	[MORTGAGEE_CONTACT_PHONE] varchar(32)  NULL ,
	[RELATIONSHIP_TERMINATED_IND] CHAR(1)  NULL ,
	[REVIEW_TYPE]        varchar(16)  NULL ,
	[RQSTD_BY_PRSNNL_ID] varchar(32)  NULL ,
	[RQSTD_BY_EMAIL_ADDRESS] varchar(255)  NULL ,
	[RVW_AUD_BEGIN_DT]   datetime  NULL ,
	[RVW_AUD_COMPLT_DT]  datetime  NULL ,
	[SUSPECTED_PARTY_ADDRESS] varchar(100)  NULL ,
	[SUSPECTED_PARTY_CITY] varchar(100)  NULL ,
	[SUSPECT_PARTY]      varchar(100)  NULL ,
	[SUSPECT_PARTY_STATE] varchar(16)  NULL ,
	[SUSPECT_PARTY_ZIP]  varchar(16)  NULL ,
	[TYPE_OF_FRAUD]      varchar(16)  NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [SELECT_REQUEST_LENDER_SELF]
	ADD CONSTRAINT [SELECT_REQUEST_LENDER_SELF_PK] PRIMARY KEY  NONCLUSTERED ([SLCTN_REQ_ID] ASC)
go

CREATE TABLE [SELECTION_UNIVERSE_CASES]
( 
	[CASE_NUMBER]        char(11)  NOT NULL ,
	[UNIVERSE_ID]        varchar(30)  NOT NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [SELECTION_UNIVERSE_CASES]
	ADD CONSTRAINT [SELECTION_UNIVERSE_CASES_PK] PRIMARY KEY  NONCLUSTERED ([CASE_NUMBER] ASC,[UNIVERSE_ID] ASC)
go

CREATE TABLE [SUPPRESSION_LIST]
( 
	[MTGEE5]             CHAR(5)  NOT NULL ,
	[CREATED_BY]         varchar(16)  NULL ,
	[UPDATED_BY]         varchar(16)  NULL ,
	[CREATED_TS]         datetime  NULL ,
	[UPDATED_TS]         datetime  NULL 
)
go

ALTER TABLE [SUPPRESSION_LIST]
	ADD CONSTRAINT [SUPPRESSION_LIST_PK] PRIMARY KEY  NONCLUSTERED ([MTGEE5] ASC)
go


ALTER TABLE [COMM_TEMPLATE_AUDIT]
	ADD CONSTRAINT [TEMPLAUDIT] FOREIGN KEY ([TEMPLATE_ID]) REFERENCES [COMM_TEMPLATE]([TEMPLATE_ID])
go


ALTER TABLE [DEFECT_CAUSE]
	ADD CONSTRAINT [DEFECTCAUSETODEFECT] FOREIGN KEY ([DEFECT_CD]) REFERENCES [DEFECT]([DEFECT_CD])
go


ALTER TABLE [DEFECT_SOURCE]
	ADD CONSTRAINT [DFCTSOURCETODEFECT] FOREIGN KEY ([DEFECT_CD]) REFERENCES [DEFECT]([DEFECT_CD])
go


ALTER TABLE [DICT_METADATA_FIELD]
	ADD CONSTRAINT [METAFIELDTOMETAENTITY] FOREIGN KEY ([ENTITY_NAME]) REFERENCES [DICT_METADATA_ENTITY]([ENTITY_NAME])
go


ALTER TABLE [DEFECT_SEVERITY_TIER]
	ADD CONSTRAINT [DFCTSEVERITYTIERTODEFECT] FOREIGN KEY ([DEFECT_CD]) REFERENCES [DEFECT]([DEFECT_CD])
go

ALTER TABLE [DEFECT_SEVERITY_TIER]
	ADD CONSTRAINT [DFCTSEVERITYTIERTODFCTCAUSE] FOREIGN KEY ([DEFECT_CAUSE_CD],[DEFECT_CD]) REFERENCES [DEFECT_CAUSE]([DEFECT_CAUSE_CD],[DEFECT_CD])
go

ALTER TABLE [DEFECT_SEVERITY_TIER]
	ADD CONSTRAINT [DFCTSEVERITYTIERTODFCTSRCE] FOREIGN KEY ([DEFECT_CD],[DFCT_SOURCE_CD]) REFERENCES [DEFECT_SOURCE]([DEFECT_CD],[DFCT_SOURCE_CD])
go

ALTER TABLE [DEFECT_SEVERITY_TIER]
	ADD CONSTRAINT [DFCTSEVERITYTIERTORATING] FOREIGN KEY ([RATING_CD]) REFERENCES [RATING]([RATING_CD])
go


ALTER TABLE [LOAN_SELECTION_CASE_SUMMARY]
	ADD CONSTRAINT [CASESUMMARYTOSELECTION] FOREIGN KEY ([SELECTION_ID]) REFERENCES [LOAN_SELECTION]([SELECTION_ID])
go


ALTER TABLE [PERSONNEL_ASSIGNMENT_TYPE]
	ADD CONSTRAINT [PERSONASSIGNTYPES] FOREIGN KEY ([ASSIGNMENT_TYPE_CD]) REFERENCES [ASSIGNMENT_TYPE_ADMIN]([ASSIGNMENT_TYPE_CD])
go

ALTER TABLE [PERSONNEL_ASSIGNMENT_TYPE]
	ADD CONSTRAINT [PERSONASSIGNTYPESPERSON] FOREIGN KEY ([PRSNNL_ID]) REFERENCES [PERSONNEL]([PRSNNL_ID])
go


ALTER TABLE [QATREE_BRANCH]
	ADD CONSTRAINT [QATREEBRANCHTOQATREE] FOREIGN KEY ([QATREE_ID]) REFERENCES [QATREE]([QATREE_ID])
go


ALTER TABLE [QATREE_OUTCM_TIERS]
	ADD CONSTRAINT [QATREEOUTCOMETOTIERS] FOREIGN KEY ([QA_OUTCM_ID]) REFERENCES [QATREE_OUTCOME]([QA_OUTCM_ID])
go


ALTER TABLE [QATREE_OUTCM_SOURCES]
	ADD CONSTRAINT [QATREEOCSRCESTOOUTCOME] FOREIGN KEY ([QA_OUTCM_ID]) REFERENCES [QATREE_OUTCOME]([QA_OUTCM_ID])
go


ALTER TABLE [QATREE_OUTCM_CAUSES]
	ADD CONSTRAINT [QATREEOCCAUSETOOUTCOME] FOREIGN KEY ([QA_OUTCM_ID]) REFERENCES [QATREE_OUTCOME]([QA_OUTCM_ID])
go


ALTER TABLE [QATREE_QUESTION]
	ADD CONSTRAINT [QUESTIONTOQATREE] FOREIGN KEY ([QATREE_ID]) REFERENCES [QATREE]([QATREE_ID])
go


ALTER TABLE [QATREE_QSTN_CONDITION]
	ADD CONSTRAINT [QATREEQSTNCONDTOMETAENTITY] FOREIGN KEY ([ENTITY_NAME]) REFERENCES [DICT_METADATA_ENTITY]([ENTITY_NAME])
go

ALTER TABLE [QATREE_QSTN_CONDITION]
	ADD CONSTRAINT [QATREEQSTNCONDTOMETAFIELD] FOREIGN KEY ([ENTITY_NAME],[FIELD_NAME]) REFERENCES [DICT_METADATA_FIELD]([ENTITY_NAME],[FIELD_NAME])
go

ALTER TABLE [QATREE_QSTN_CONDITION]
	ADD CONSTRAINT [QATREEQSTNCONDTOQATREE] FOREIGN KEY ([QATREE_ID]) REFERENCES [QATREE]([QATREE_ID])
go

ALTER TABLE [QATREE_QSTN_CONDITION]
	ADD CONSTRAINT [QATREEQSTNCONDTOQAQSTN] FOREIGN KEY ([QATREE_ID],[QUESTION_ID]) REFERENCES [QATREE_QUESTION]([QUESTION_ID],[QATREE_ID])
go


ALTER TABLE [QATREE_QSTN_CAPTURE]
	ADD CONSTRAINT [QATREEQSTNCAPTTOMETAENTITY] FOREIGN KEY ([ENTITY_NAME]) REFERENCES [DICT_METADATA_ENTITY]([ENTITY_NAME])
go

ALTER TABLE [QATREE_QSTN_CAPTURE]
	ADD CONSTRAINT [QATREEQSTNCAPTTOMETAFIELD] FOREIGN KEY ([ENTITY_NAME],[FIELD_NAME]) REFERENCES [DICT_METADATA_FIELD]([ENTITY_NAME],[FIELD_NAME])
go

ALTER TABLE [QATREE_QSTN_CAPTURE]
	ADD CONSTRAINT [QATREEQSTNCAPTTOQATREE] FOREIGN KEY ([QATREE_ID]) REFERENCES [QATREE]([QATREE_ID])
go

ALTER TABLE [QATREE_QSTN_CAPTURE]
	ADD CONSTRAINT [QATREEQSTNCAPTTOQAQSTN] FOREIGN KEY ([QATREE_ID],[QUESTION_ID]) REFERENCES [QATREE_QUESTION]([QUESTION_ID],[QATREE_ID])
go


ALTER TABLE [REVIEW]
	ADD CONSTRAINT [REVIEWBATCH] FOREIGN KEY ([BATCH_ID]) REFERENCES [RVW_BATCH]([BATCH_ID])
go

ALTER TABLE [REVIEW]
	ADD CONSTRAINT [REVIEWSELECTION] FOREIGN KEY ([SELECTION_ID]) REFERENCES [LOAN_SELECTION]([SELECTION_ID])
go


ALTER TABLE [RVW_LVL_AUDIT]
	ADD CONSTRAINT [REVIEWLEVELTOAUDIT] FOREIGN KEY ([REVIEW_LVL_ID]) REFERENCES [REVIEW_LEVEL]([REVIEW_LVL_ID])
go


ALTER TABLE [RVW_LVL_FINDING]
	ADD CONSTRAINT [RVWLVLFINDINGTODEFECT] FOREIGN KEY ([DEFECT_CD]) REFERENCES [DEFECT]([DEFECT_CD])
go

ALTER TABLE [RVW_LVL_FINDING]
	ADD CONSTRAINT [RVWLVLFINDINGTODFCTCAUSE] FOREIGN KEY ([DEFECT_CAUSE_CD],[DEFECT_CD]) REFERENCES [DEFECT_CAUSE]([DEFECT_CAUSE_CD],[DEFECT_CD])
go

ALTER TABLE [RVW_LVL_FINDING]
	ADD CONSTRAINT [RVWLVLFINDINGTODFCTSRCE] FOREIGN KEY ([DEFECT_CD],[DFCT_SOURCE_CD]) REFERENCES [DEFECT_SOURCE]([DEFECT_CD],[DFCT_SOURCE_CD])
go

ALTER TABLE [RVW_LVL_FINDING]
	ADD CONSTRAINT [RVWLVLFINDINGTORVWLVL] FOREIGN KEY ([REVIEW_LVL_ID]) REFERENCES [REVIEW_LEVEL]([REVIEW_LVL_ID])
go


ALTER TABLE [RVW_LVL_FINDING_AUD]
	ADD CONSTRAINT [RVWLVLFINDAUDITTORVWLEVEL] FOREIGN KEY ([REVIEW_LVL_ID]) REFERENCES [REVIEW_LEVEL]([REVIEW_LVL_ID])
go

ALTER TABLE [RVW_LVL_FINDING_AUD]
	ADD CONSTRAINT [RVWLVLFINDAUDTODFCTSRCE] FOREIGN KEY ([DEFECT_CD],[DFCT_SOURCE_CD]) REFERENCES [DEFECT_SOURCE]([DEFECT_CD],[DFCT_SOURCE_CD])
go


ALTER TABLE [RVW_LVL_RFRRL_AUD]
	ADD CONSTRAINT [RVWLVLREFERRALTORVWLEVEL] FOREIGN KEY ([REVIEW_LVL_ID]) REFERENCES [REVIEW_LEVEL]([REVIEW_LVL_ID])
go


ALTER TABLE [RVW_DEFECT]
	ADD CONSTRAINT [REVIEWDEFECTTODEFECT] FOREIGN KEY ([DEFECT_CD]) REFERENCES [DEFECT]([DEFECT_CD])
go

ALTER TABLE [RVW_DEFECT]
	ADD CONSTRAINT [REVIEWDEFECTTOREVIEW] FOREIGN KEY ([REVIEW_ID]) REFERENCES [REVIEW]([REVIEW_ID])
go


ALTER TABLE [RQST_FROM_LENDER]
	ADD CONSTRAINT [LENDBINDREQ] FOREIGN KEY ([SLCTN_REQ_ID]) REFERENCES [REQUESTED_BINDER]([SLCTN_REQ_ID])
go


ALTER TABLE [RVW_BATCH_PRSNNL]
	ADD CONSTRAINT [REVIEWBATCHTOPERSONNEL] FOREIGN KEY ([PRSNNL_ID]) REFERENCES [PERSONNEL]([PRSNNL_ID])
go


ALTER TABLE [QATREE_QSTN_RULES]
	ADD CONSTRAINT [QUESTIONRULESTOSELREASON] FOREIGN KEY ([SLCTN_REASON_ID]) REFERENCES [SELECTION_PARAMETERS]([SLCTN_REASON_ID])
go


ALTER TABLE [LOAN_SELECTION_PENDING]
	ADD CONSTRAINT [PENDSELECTIONTOREASON] FOREIGN KEY ([SLCTN_REASON_ID]) REFERENCES [SELECTION_PARAMETERS]([SLCTN_REASON_ID])
go


ALTER TABLE [DELIV_PARMS_REQUEST]
	ADD CONSTRAINT [DELIVPARMSREQREVLEVEL] FOREIGN KEY ([REVIEW_LEVEL_CD]) REFERENCES [REVIEW_LEVEL_ADMIN]([REVIEW_LEVEL_CD])
go

ALTER TABLE [DELIV_PARMS_REQUEST]
	ADD CONSTRAINT [DELIVPARMSREQSELPARMS] FOREIGN KEY ([SLCTN_REASON_ID]) REFERENCES [SELECTION_PARAMETERS]([SLCTN_REASON_ID])
go


ALTER TABLE [DELIV_PARMS_ITERATION]
	ADD CONSTRAINT [DELIVPARMSREVLEVEL] FOREIGN KEY ([REVIEW_LEVEL_CD]) REFERENCES [REVIEW_LEVEL_ADMIN]([REVIEW_LEVEL_CD])
go

ALTER TABLE [DELIV_PARMS_ITERATION]
	ADD CONSTRAINT [DELIVPARMSSELPARMS] FOREIGN KEY ([SLCTN_REASON_ID]) REFERENCES [SELECTION_PARAMETERS]([SLCTN_REASON_ID])
go


ALTER TABLE [RVW_LVL_DEFECT]
	ADD CONSTRAINT [REVIEWLEVELDEFECTTOAUDIT] FOREIGN KEY ([DEFECT_CD]) REFERENCES [DEFECT]([DEFECT_CD])
go

ALTER TABLE [RVW_LVL_DEFECT]
	ADD CONSTRAINT [REVIEWLEVELDEFECTTORVWLVL] FOREIGN KEY ([REVIEW_LVL_ID]) REFERENCES [REVIEW_LEVEL]([REVIEW_LVL_ID])
go

ALTER TABLE [RVW_LVL_DEFECT]
	ADD CONSTRAINT [REVIEWLEVELDEFECTTOREVIEW] FOREIGN KEY ([REVIEW_ID]) REFERENCES [REVIEW]([REVIEW_ID])
go


ALTER TABLE [RVW_LVL_DEFECT_AUD]
	ADD CONSTRAINT [RVWLVLDEFAUDTODEFECT] FOREIGN KEY ([DEFECT_CD]) REFERENCES [DEFECT]([DEFECT_CD])
go

ALTER TABLE [RVW_LVL_DEFECT_AUD]
	ADD CONSTRAINT [RVWLVLDEFAUDTOREVIEW] FOREIGN KEY ([REVIEW_ID]) REFERENCES [REVIEW]([REVIEW_ID])
go

ALTER TABLE [RVW_LVL_DEFECT_AUD]
	ADD CONSTRAINT [RVWLVLDEFAUDTOREVIEWLEVEL] FOREIGN KEY ([REVIEW_LVL_ID]) REFERENCES [REVIEW_LEVEL]([REVIEW_LVL_ID])
go

ALTER TABLE [RVW_LVL_DEFECT_AUD]
	ADD CONSTRAINT [RVWLVLDEFAUDTORVWLVLDEF] FOREIGN KEY ([REVIEW_LVL_ID],[DEFECT_CD]) REFERENCES [RVW_LVL_DEFECT]([REVIEW_LVL_ID],[DEFECT_CD])
go


ALTER TABLE [RVW_LVL_INDEM]
	ADD CONSTRAINT [RVWLVLINDEMTORVWLEVEL] FOREIGN KEY ([REVIEW_LVL_ID]) REFERENCES [REVIEW_LEVEL]([REVIEW_LVL_ID])
go


ALTER TABLE [RVW_LVL_INDEM_AUD]
	ADD CONSTRAINT [RVWLVLINDEMAUDITTORVWLEVEL] FOREIGN KEY ([REVIEW_LVL_ID]) REFERENCES [REVIEW_LEVEL]([REVIEW_LVL_ID])
go

ALTER TABLE [RVW_LVL_INDEM_AUD]
	ADD CONSTRAINT [RVWLVLINDEMAUDTORVWLVLIND] FOREIGN KEY ([REVIEW_LVL_ID],[INDEM_TYPE]) REFERENCES [RVW_LVL_INDEM]([REVIEW_LVL_ID],[INDEM_TYPE])
go


ALTER TABLE [SCORING_MODEL_FACTORS]
	ADD CONSTRAINT [MODELFACTORSTOMODEL] FOREIGN KEY ([MODEL_ID]) REFERENCES [SCORING_MODEL]([MODEL_ID])
go

ALTER TABLE [SCORING_MODEL_FACTORS]
	ADD CONSTRAINT [SCORMODLFACTTOMETAENTITY] FOREIGN KEY ([ENTITY_NAME]) REFERENCES [DICT_METADATA_ENTITY]([ENTITY_NAME])
go

ALTER TABLE [SCORING_MODEL_FACTORS]
	ADD CONSTRAINT [SCORMODLFACTTOMETAFIELD] FOREIGN KEY ([ENTITY_NAME],[FIELD_NAME]) REFERENCES [DICT_METADATA_FIELD]([ENTITY_NAME],[FIELD_NAME])
go


ALTER TABLE [MODEL_SCORE]
	ADD CONSTRAINT [SCORETOMODEL] FOREIGN KEY ([MODEL_ID]) REFERENCES [SCORING_MODEL]([MODEL_ID])
go


ALTER TABLE [SCORING_MODEL_VERSION]
	ADD CONSTRAINT [MODELVERSIONTOMODEL] FOREIGN KEY ([MODEL_ID]) REFERENCES [SCORING_MODEL]([MODEL_ID])
go


CREATE TRIGGER tD_ASSIGNMENT_TYPE_ADMIN ON ASSIGNMENT_TYPE_ADMIN FOR DELETE AS
/* ERwin Builtin Trigger */
/* DELETE trigger on ASSIGNMENT_TYPE_ADMIN */
BEGIN
  DECLARE  @errno   int,
           @errmsg  varchar(255)
    /* ERwin Builtin Trigger */
    /* ASSIGNMENT_TYPE_ADMIN  PERSONNEL_ASSIGNMENT_TYPE on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="000134e4", PARENT_OWNER="", PARENT_TABLE="ASSIGNMENT_TYPE_ADMIN"
    CHILD_OWNER="", CHILD_TABLE="PERSONNEL_ASSIGNMENT_TYPE"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="PERSONASSIGNTYPES", FK_COLUMNS="ASSIGNMENT_TYPE_CD" */
    IF EXISTS (
      SELECT * FROM deleted,PERSONNEL_ASSIGNMENT_TYPE
      WHERE
        /*  %JoinFKPK(PERSONNEL_ASSIGNMENT_TYPE,deleted," = "," AND") */
        PERSONNEL_ASSIGNMENT_TYPE.ASSIGNMENT_TYPE_CD = deleted.ASSIGNMENT_TYPE_CD
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete ASSIGNMENT_TYPE_ADMIN because PERSONNEL_ASSIGNMENT_TYPE exists.'
      GOTO error
    END


    /* ERwin Builtin Trigger */
    RETURN
error:
    raiserror (@errmsg,16,-1)
    rollback transaction
END

go


CREATE TRIGGER tU_ASSIGNMENT_TYPE_ADMIN ON ASSIGNMENT_TYPE_ADMIN FOR UPDATE AS
/* ERwin Builtin Trigger */
/* UPDATE trigger on ASSIGNMENT_TYPE_ADMIN */
BEGIN
  DECLARE  @numrows int,
           @nullcnt int,
           @validcnt int,
           @insASSIGNMENT_TYPE_CD varchar(16),
           @errno   int,
           @errmsg  varchar(255)

  SELECT @numrows = @@rowcount
  /* ERwin Builtin Trigger */
  /* ASSIGNMENT_TYPE_ADMIN  PERSONNEL_ASSIGNMENT_TYPE on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="00015ccf", PARENT_OWNER="", PARENT_TABLE="ASSIGNMENT_TYPE_ADMIN"
    CHILD_OWNER="", CHILD_TABLE="PERSONNEL_ASSIGNMENT_TYPE"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="PERSONASSIGNTYPES", FK_COLUMNS="ASSIGNMENT_TYPE_CD" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(ASSIGNMENT_TYPE_CD)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,PERSONNEL_ASSIGNMENT_TYPE
      WHERE
        /*  %JoinFKPK(PERSONNEL_ASSIGNMENT_TYPE,deleted," = "," AND") */
        PERSONNEL_ASSIGNMENT_TYPE.ASSIGNMENT_TYPE_CD = deleted.ASSIGNMENT_TYPE_CD
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update ASSIGNMENT_TYPE_ADMIN because PERSONNEL_ASSIGNMENT_TYPE exists.'
      GOTO error
    END
  END


  /* ERwin Builtin Trigger */
  RETURN
error:
    raiserror (@errmsg,16,-1)
    rollback transaction
END

go




CREATE TRIGGER tD_COMM_TEMPLATE ON COMM_TEMPLATE FOR DELETE AS
/* ERwin Builtin Trigger */
/* DELETE trigger on COMM_TEMPLATE */
BEGIN
  DECLARE  @errno   int,
           @errmsg  varchar(255)
    /* ERwin Builtin Trigger */
    /* COMM_TEMPLATE  COMM_TEMPLATE_AUDIT on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00010d51", PARENT_OWNER="", PARENT_TABLE="COMM_TEMPLATE"
    CHILD_OWNER="", CHILD_TABLE="COMM_TEMPLATE_AUDIT"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="TEMPLAUDIT", FK_COLUMNS="TEMPLATE_ID" */
    IF EXISTS (
      SELECT * FROM deleted,COMM_TEMPLATE_AUDIT
      WHERE
        /*  %JoinFKPK(COMM_TEMPLATE_AUDIT,deleted," = "," AND") */
        COMM_TEMPLATE_AUDIT.TEMPLATE_ID = deleted.TEMPLATE_ID
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete COMM_TEMPLATE because COMM_TEMPLATE_AUDIT exists.'
      GOTO error
    END


    /* ERwin Builtin Trigger */
    RETURN
error:
    raiserror (@errmsg,16,-1)
    rollback transaction
END

go


CREATE TRIGGER tU_COMM_TEMPLATE ON COMM_TEMPLATE FOR UPDATE AS
/* ERwin Builtin Trigger */
/* UPDATE trigger on COMM_TEMPLATE */
BEGIN
  DECLARE  @numrows int,
           @nullcnt int,
           @validcnt int,
           @insTEMPLATE_ID varchar(16),
           @errno   int,
           @errmsg  varchar(255)

  SELECT @numrows = @@rowcount
  /* ERwin Builtin Trigger */
  /* COMM_TEMPLATE  COMM_TEMPLATE_AUDIT on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="00012ef5", PARENT_OWNER="", PARENT_TABLE="COMM_TEMPLATE"
    CHILD_OWNER="", CHILD_TABLE="COMM_TEMPLATE_AUDIT"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="TEMPLAUDIT", FK_COLUMNS="TEMPLATE_ID" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(TEMPLATE_ID)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,COMM_TEMPLATE_AUDIT
      WHERE
        /*  %JoinFKPK(COMM_TEMPLATE_AUDIT,deleted," = "," AND") */
        COMM_TEMPLATE_AUDIT.TEMPLATE_ID = deleted.TEMPLATE_ID
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update COMM_TEMPLATE because COMM_TEMPLATE_AUDIT exists.'
      GOTO error
    END
  END


  /* ERwin Builtin Trigger */
  RETURN
error:
    raiserror (@errmsg,16,-1)
    rollback transaction
END

go




CREATE TRIGGER tD_DEFECT ON DEFECT FOR DELETE AS
/* ERwin Builtin Trigger */
/* DELETE trigger on DEFECT */
BEGIN
  DECLARE  @errno   int,
           @errmsg  varchar(255)
    /* ERwin Builtin Trigger */
    /* DEFECT  RVW_LVL_FINDING on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00072042", PARENT_OWNER="", PARENT_TABLE="DEFECT"
    CHILD_OWNER="", CHILD_TABLE="RVW_LVL_FINDING"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="RVWLVLFINDINGTODEFECT", FK_COLUMNS="DEFECT_CD" */
    IF EXISTS (
      SELECT * FROM deleted,RVW_LVL_FINDING
      WHERE
        /*  %JoinFKPK(RVW_LVL_FINDING,deleted," = "," AND") */
        RVW_LVL_FINDING.DEFECT_CD = deleted.DEFECT_CD
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete DEFECT because RVW_LVL_FINDING exists.'
      GOTO error
    END

    /* ERwin Builtin Trigger */
    /* DEFECT  RVW_LVL_DEFECT_AUD on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="DEFECT"
    CHILD_OWNER="", CHILD_TABLE="RVW_LVL_DEFECT_AUD"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="RVWLVLDEFAUDTODEFECT", FK_COLUMNS="DEFECT_CD" */
    IF EXISTS (
      SELECT * FROM deleted,RVW_LVL_DEFECT_AUD
      WHERE
        /*  %JoinFKPK(RVW_LVL_DEFECT_AUD,deleted," = "," AND") */
        RVW_LVL_DEFECT_AUD.DEFECT_CD = deleted.DEFECT_CD
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete DEFECT because RVW_LVL_DEFECT_AUD exists.'
      GOTO error
    END

    /* ERwin Builtin Trigger */
    /* DEFECT  RVW_LVL_DEFECT on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="DEFECT"
    CHILD_OWNER="", CHILD_TABLE="RVW_LVL_DEFECT"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="REVIEWLEVELDEFECTTOAUDIT", FK_COLUMNS="DEFECT_CD" */
    IF EXISTS (
      SELECT * FROM deleted,RVW_LVL_DEFECT
      WHERE
        /*  %JoinFKPK(RVW_LVL_DEFECT,deleted," = "," AND") */
        RVW_LVL_DEFECT.DEFECT_CD = deleted.DEFECT_CD
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete DEFECT because RVW_LVL_DEFECT exists.'
      GOTO error
    END

    /* ERwin Builtin Trigger */
    /* DEFECT  RVW_DEFECT on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="DEFECT"
    CHILD_OWNER="", CHILD_TABLE="RVW_DEFECT"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="REVIEWDEFECTTODEFECT", FK_COLUMNS="DEFECT_CD" */
    IF EXISTS (
      SELECT * FROM deleted,RVW_DEFECT
      WHERE
        /*  %JoinFKPK(RVW_DEFECT,deleted," = "," AND") */
        RVW_DEFECT.DEFECT_CD = deleted.DEFECT_CD
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete DEFECT because RVW_DEFECT exists.'
      GOTO error
    END

    /* ERwin Builtin Trigger */
    /* DEFECT  DEFECT_SOURCE on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="DEFECT"
    CHILD_OWNER="", CHILD_TABLE="DEFECT_SOURCE"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="DFCTSOURCETODEFECT", FK_COLUMNS="DEFECT_CD" */
    IF EXISTS (
      SELECT * FROM deleted,DEFECT_SOURCE
      WHERE
        /*  %JoinFKPK(DEFECT_SOURCE,deleted," = "," AND") */
        DEFECT_SOURCE.DEFECT_CD = deleted.DEFECT_CD
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete DEFECT because DEFECT_SOURCE exists.'
      GOTO error
    END

    /* ERwin Builtin Trigger */
    /* DEFECT  DEFECT_SEVERITY_TIER on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="DEFECT"
    CHILD_OWNER="", CHILD_TABLE="DEFECT_SEVERITY_TIER"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="DFCTSEVERITYTIERTODEFECT", FK_COLUMNS="DEFECT_CD" */
    IF EXISTS (
      SELECT * FROM deleted,DEFECT_SEVERITY_TIER
      WHERE
        /*  %JoinFKPK(DEFECT_SEVERITY_TIER,deleted," = "," AND") */
        DEFECT_SEVERITY_TIER.DEFECT_CD = deleted.DEFECT_CD
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete DEFECT because DEFECT_SEVERITY_TIER exists.'
      GOTO error
    END

    /* ERwin Builtin Trigger */
    /* DEFECT  DEFECT_CAUSE on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="DEFECT"
    CHILD_OWNER="", CHILD_TABLE="DEFECT_CAUSE"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="DEFECTCAUSETODEFECT", FK_COLUMNS="DEFECT_CD" */
    IF EXISTS (
      SELECT * FROM deleted,DEFECT_CAUSE
      WHERE
        /*  %JoinFKPK(DEFECT_CAUSE,deleted," = "," AND") */
        DEFECT_CAUSE.DEFECT_CD = deleted.DEFECT_CD
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete DEFECT because DEFECT_CAUSE exists.'
      GOTO error
    END


    /* ERwin Builtin Trigger */
    RETURN
error:
    raiserror (@errmsg,16,-1)
    rollback transaction
END

go


CREATE TRIGGER tU_DEFECT ON DEFECT FOR UPDATE AS
/* ERwin Builtin Trigger */
/* UPDATE trigger on DEFECT */
BEGIN
  DECLARE  @numrows int,
           @nullcnt int,
           @validcnt int,
           @insDEFECT_CD varchar(16),
           @errno   int,
           @errmsg  varchar(255)

  SELECT @numrows = @@rowcount
  /* ERwin Builtin Trigger */
  /* DEFECT  RVW_LVL_FINDING on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="0007d8cf", PARENT_OWNER="", PARENT_TABLE="DEFECT"
    CHILD_OWNER="", CHILD_TABLE="RVW_LVL_FINDING"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="RVWLVLFINDINGTODEFECT", FK_COLUMNS="DEFECT_CD" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(DEFECT_CD)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,RVW_LVL_FINDING
      WHERE
        /*  %JoinFKPK(RVW_LVL_FINDING,deleted," = "," AND") */
        RVW_LVL_FINDING.DEFECT_CD = deleted.DEFECT_CD
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update DEFECT because RVW_LVL_FINDING exists.'
      GOTO error
    END
  END

  /* ERwin Builtin Trigger */
  /* DEFECT  RVW_LVL_DEFECT_AUD on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="DEFECT"
    CHILD_OWNER="", CHILD_TABLE="RVW_LVL_DEFECT_AUD"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="RVWLVLDEFAUDTODEFECT", FK_COLUMNS="DEFECT_CD" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(DEFECT_CD)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,RVW_LVL_DEFECT_AUD
      WHERE
        /*  %JoinFKPK(RVW_LVL_DEFECT_AUD,deleted," = "," AND") */
        RVW_LVL_DEFECT_AUD.DEFECT_CD = deleted.DEFECT_CD
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update DEFECT because RVW_LVL_DEFECT_AUD exists.'
      GOTO error
    END
  END

  /* ERwin Builtin Trigger */
  /* DEFECT  RVW_LVL_DEFECT on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="DEFECT"
    CHILD_OWNER="", CHILD_TABLE="RVW_LVL_DEFECT"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="REVIEWLEVELDEFECTTOAUDIT", FK_COLUMNS="DEFECT_CD" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(DEFECT_CD)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,RVW_LVL_DEFECT
      WHERE
        /*  %JoinFKPK(RVW_LVL_DEFECT,deleted," = "," AND") */
        RVW_LVL_DEFECT.DEFECT_CD = deleted.DEFECT_CD
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update DEFECT because RVW_LVL_DEFECT exists.'
      GOTO error
    END
  END

  /* ERwin Builtin Trigger */
  /* DEFECT  RVW_DEFECT on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="DEFECT"
    CHILD_OWNER="", CHILD_TABLE="RVW_DEFECT"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="REVIEWDEFECTTODEFECT", FK_COLUMNS="DEFECT_CD" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(DEFECT_CD)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,RVW_DEFECT
      WHERE
        /*  %JoinFKPK(RVW_DEFECT,deleted," = "," AND") */
        RVW_DEFECT.DEFECT_CD = deleted.DEFECT_CD
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update DEFECT because RVW_DEFECT exists.'
      GOTO error
    END
  END

  /* ERwin Builtin Trigger */
  /* DEFECT  DEFECT_SOURCE on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="DEFECT"
    CHILD_OWNER="", CHILD_TABLE="DEFECT_SOURCE"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="DFCTSOURCETODEFECT", FK_COLUMNS="DEFECT_CD" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(DEFECT_CD)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,DEFECT_SOURCE
      WHERE
        /*  %JoinFKPK(DEFECT_SOURCE,deleted," = "," AND") */
        DEFECT_SOURCE.DEFECT_CD = deleted.DEFECT_CD
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update DEFECT because DEFECT_SOURCE exists.'
      GOTO error
    END
  END

  /* ERwin Builtin Trigger */
  /* DEFECT  DEFECT_SEVERITY_TIER on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="DEFECT"
    CHILD_OWNER="", CHILD_TABLE="DEFECT_SEVERITY_TIER"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="DFCTSEVERITYTIERTODEFECT", FK_COLUMNS="DEFECT_CD" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(DEFECT_CD)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,DEFECT_SEVERITY_TIER
      WHERE
        /*  %JoinFKPK(DEFECT_SEVERITY_TIER,deleted," = "," AND") */
        DEFECT_SEVERITY_TIER.DEFECT_CD = deleted.DEFECT_CD
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update DEFECT because DEFECT_SEVERITY_TIER exists.'
      GOTO error
    END
  END

  /* ERwin Builtin Trigger */
  /* DEFECT  DEFECT_CAUSE on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="DEFECT"
    CHILD_OWNER="", CHILD_TABLE="DEFECT_CAUSE"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="DEFECTCAUSETODEFECT", FK_COLUMNS="DEFECT_CD" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(DEFECT_CD)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,DEFECT_CAUSE
      WHERE
        /*  %JoinFKPK(DEFECT_CAUSE,deleted," = "," AND") */
        DEFECT_CAUSE.DEFECT_CD = deleted.DEFECT_CD
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update DEFECT because DEFECT_CAUSE exists.'
      GOTO error
    END
  END


  /* ERwin Builtin Trigger */
  RETURN
error:
    raiserror (@errmsg,16,-1)
    rollback transaction
END

go




CREATE TRIGGER tD_DEFECT_CAUSE ON DEFECT_CAUSE FOR DELETE AS
/* ERwin Builtin Trigger */
/* DELETE trigger on DEFECT_CAUSE */
BEGIN
  DECLARE  @errno   int,
           @errmsg  varchar(255)
    /* ERwin Builtin Trigger */
    /* DEFECT_CAUSE  RVW_LVL_FINDING on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00026fa4", PARENT_OWNER="", PARENT_TABLE="DEFECT_CAUSE"
    CHILD_OWNER="", CHILD_TABLE="RVW_LVL_FINDING"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="RVWLVLFINDINGTODFCTCAUSE", FK_COLUMNS="DEFECT_CAUSE_CD""DEFECT_CD" */
    IF EXISTS (
      SELECT * FROM deleted,RVW_LVL_FINDING
      WHERE
        /*  %JoinFKPK(RVW_LVL_FINDING,deleted," = "," AND") */
        RVW_LVL_FINDING.DEFECT_CAUSE_CD = deleted.DEFECT_CAUSE_CD AND
        RVW_LVL_FINDING.DEFECT_CD = deleted.DEFECT_CD
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete DEFECT_CAUSE because RVW_LVL_FINDING exists.'
      GOTO error
    END

    /* ERwin Builtin Trigger */
    /* DEFECT_CAUSE  DEFECT_SEVERITY_TIER on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="DEFECT_CAUSE"
    CHILD_OWNER="", CHILD_TABLE="DEFECT_SEVERITY_TIER"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="DFCTSEVERITYTIERTODFCTCAUSE", FK_COLUMNS="DEFECT_CAUSE_CD""DEFECT_CD" */
    IF EXISTS (
      SELECT * FROM deleted,DEFECT_SEVERITY_TIER
      WHERE
        /*  %JoinFKPK(DEFECT_SEVERITY_TIER,deleted," = "," AND") */
        DEFECT_SEVERITY_TIER.DEFECT_CAUSE_CD = deleted.DEFECT_CAUSE_CD AND
        DEFECT_SEVERITY_TIER.DEFECT_CD = deleted.DEFECT_CD
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete DEFECT_CAUSE because DEFECT_SEVERITY_TIER exists.'
      GOTO error
    END


    /* ERwin Builtin Trigger */
    RETURN
error:
    raiserror (@errmsg,16,-1)
    rollback transaction
END

go


CREATE TRIGGER tU_DEFECT_CAUSE ON DEFECT_CAUSE FOR UPDATE AS
/* ERwin Builtin Trigger */
/* UPDATE trigger on DEFECT_CAUSE */
BEGIN
  DECLARE  @numrows int,
           @nullcnt int,
           @validcnt int,
           @insDEFECT_CAUSE_CD varchar(16), 
           @insDEFECT_CD varchar(16),
           @errno   int,
           @errmsg  varchar(255)

  SELECT @numrows = @@rowcount
  /* ERwin Builtin Trigger */
  /* DEFECT_CAUSE  RVW_LVL_FINDING on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="0002bcae", PARENT_OWNER="", PARENT_TABLE="DEFECT_CAUSE"
    CHILD_OWNER="", CHILD_TABLE="RVW_LVL_FINDING"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="RVWLVLFINDINGTODFCTCAUSE", FK_COLUMNS="DEFECT_CAUSE_CD""DEFECT_CD" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(DEFECT_CAUSE_CD) OR
    UPDATE(DEFECT_CD)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,RVW_LVL_FINDING
      WHERE
        /*  %JoinFKPK(RVW_LVL_FINDING,deleted," = "," AND") */
        RVW_LVL_FINDING.DEFECT_CAUSE_CD = deleted.DEFECT_CAUSE_CD AND
        RVW_LVL_FINDING.DEFECT_CD = deleted.DEFECT_CD
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update DEFECT_CAUSE because RVW_LVL_FINDING exists.'
      GOTO error
    END
  END

  /* ERwin Builtin Trigger */
  /* DEFECT_CAUSE  DEFECT_SEVERITY_TIER on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="DEFECT_CAUSE"
    CHILD_OWNER="", CHILD_TABLE="DEFECT_SEVERITY_TIER"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="DFCTSEVERITYTIERTODFCTCAUSE", FK_COLUMNS="DEFECT_CAUSE_CD""DEFECT_CD" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(DEFECT_CAUSE_CD) OR
    UPDATE(DEFECT_CD)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,DEFECT_SEVERITY_TIER
      WHERE
        /*  %JoinFKPK(DEFECT_SEVERITY_TIER,deleted," = "," AND") */
        DEFECT_SEVERITY_TIER.DEFECT_CAUSE_CD = deleted.DEFECT_CAUSE_CD AND
        DEFECT_SEVERITY_TIER.DEFECT_CD = deleted.DEFECT_CD
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update DEFECT_CAUSE because DEFECT_SEVERITY_TIER exists.'
      GOTO error
    END
  END


  /* ERwin Builtin Trigger */
  RETURN
error:
    raiserror (@errmsg,16,-1)
    rollback transaction
END

go




CREATE TRIGGER tD_DEFECT_SOURCE ON DEFECT_SOURCE FOR DELETE AS
/* ERwin Builtin Trigger */
/* DELETE trigger on DEFECT_SOURCE */
BEGIN
  DECLARE  @errno   int,
           @errmsg  varchar(255)
    /* ERwin Builtin Trigger */
    /* DEFECT_SOURCE  RVW_LVL_FINDING on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="0003be42", PARENT_OWNER="", PARENT_TABLE="DEFECT_SOURCE"
    CHILD_OWNER="", CHILD_TABLE="RVW_LVL_FINDING"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="RVWLVLFINDINGTODFCTSRCE", FK_COLUMNS="DEFECT_CD""DFCT_SOURCE_CD" */
    IF EXISTS (
      SELECT * FROM deleted,RVW_LVL_FINDING
      WHERE
        /*  %JoinFKPK(RVW_LVL_FINDING,deleted," = "," AND") */
        RVW_LVL_FINDING.DEFECT_CD = deleted.DEFECT_CD AND
        RVW_LVL_FINDING.DFCT_SOURCE_CD = deleted.DFCT_SOURCE_CD
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete DEFECT_SOURCE because RVW_LVL_FINDING exists.'
      GOTO error
    END

    /* ERwin Builtin Trigger */
    /* DEFECT_SOURCE  RVW_LVL_FINDING_AUD on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="DEFECT_SOURCE"
    CHILD_OWNER="", CHILD_TABLE="RVW_LVL_FINDING_AUD"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="RVWLVLFINDAUDTODFCTSRCE", FK_COLUMNS="DEFECT_CD""DFCT_SOURCE_CD" */
    IF EXISTS (
      SELECT * FROM deleted,RVW_LVL_FINDING_AUD
      WHERE
        /*  %JoinFKPK(RVW_LVL_FINDING_AUD,deleted," = "," AND") */
        RVW_LVL_FINDING_AUD.DEFECT_CD = deleted.DEFECT_CD AND
        RVW_LVL_FINDING_AUD.DFCT_SOURCE_CD = deleted.DFCT_SOURCE_CD
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete DEFECT_SOURCE because RVW_LVL_FINDING_AUD exists.'
      GOTO error
    END

    /* ERwin Builtin Trigger */
    /* DEFECT_SOURCE  DEFECT_SEVERITY_TIER on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="DEFECT_SOURCE"
    CHILD_OWNER="", CHILD_TABLE="DEFECT_SEVERITY_TIER"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="DFCTSEVERITYTIERTODFCTSRCE", FK_COLUMNS="DEFECT_CD""DFCT_SOURCE_CD" */
    IF EXISTS (
      SELECT * FROM deleted,DEFECT_SEVERITY_TIER
      WHERE
        /*  %JoinFKPK(DEFECT_SEVERITY_TIER,deleted," = "," AND") */
        DEFECT_SEVERITY_TIER.DEFECT_CD = deleted.DEFECT_CD AND
        DEFECT_SEVERITY_TIER.DFCT_SOURCE_CD = deleted.DFCT_SOURCE_CD
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete DEFECT_SOURCE because DEFECT_SEVERITY_TIER exists.'
      GOTO error
    END


    /* ERwin Builtin Trigger */
    RETURN
error:
    raiserror (@errmsg,16,-1)
    rollback transaction
END

go


CREATE TRIGGER tU_DEFECT_SOURCE ON DEFECT_SOURCE FOR UPDATE AS
/* ERwin Builtin Trigger */
/* UPDATE trigger on DEFECT_SOURCE */
BEGIN
  DECLARE  @numrows int,
           @nullcnt int,
           @validcnt int,
           @insDEFECT_CD varchar(16), 
           @insDFCT_SOURCE_CD varchar(16),
           @errno   int,
           @errmsg  varchar(255)

  SELECT @numrows = @@rowcount
  /* ERwin Builtin Trigger */
  /* DEFECT_SOURCE  RVW_LVL_FINDING on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="00042a96", PARENT_OWNER="", PARENT_TABLE="DEFECT_SOURCE"
    CHILD_OWNER="", CHILD_TABLE="RVW_LVL_FINDING"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="RVWLVLFINDINGTODFCTSRCE", FK_COLUMNS="DEFECT_CD""DFCT_SOURCE_CD" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(DEFECT_CD) OR
    UPDATE(DFCT_SOURCE_CD)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,RVW_LVL_FINDING
      WHERE
        /*  %JoinFKPK(RVW_LVL_FINDING,deleted," = "," AND") */
        RVW_LVL_FINDING.DEFECT_CD = deleted.DEFECT_CD AND
        RVW_LVL_FINDING.DFCT_SOURCE_CD = deleted.DFCT_SOURCE_CD
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update DEFECT_SOURCE because RVW_LVL_FINDING exists.'
      GOTO error
    END
  END

  /* ERwin Builtin Trigger */
  /* DEFECT_SOURCE  RVW_LVL_FINDING_AUD on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="DEFECT_SOURCE"
    CHILD_OWNER="", CHILD_TABLE="RVW_LVL_FINDING_AUD"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="RVWLVLFINDAUDTODFCTSRCE", FK_COLUMNS="DEFECT_CD""DFCT_SOURCE_CD" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(DEFECT_CD) OR
    UPDATE(DFCT_SOURCE_CD)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,RVW_LVL_FINDING_AUD
      WHERE
        /*  %JoinFKPK(RVW_LVL_FINDING_AUD,deleted," = "," AND") */
        RVW_LVL_FINDING_AUD.DEFECT_CD = deleted.DEFECT_CD AND
        RVW_LVL_FINDING_AUD.DFCT_SOURCE_CD = deleted.DFCT_SOURCE_CD
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update DEFECT_SOURCE because RVW_LVL_FINDING_AUD exists.'
      GOTO error
    END
  END

  /* ERwin Builtin Trigger */
  /* DEFECT_SOURCE  DEFECT_SEVERITY_TIER on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="DEFECT_SOURCE"
    CHILD_OWNER="", CHILD_TABLE="DEFECT_SEVERITY_TIER"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="DFCTSEVERITYTIERTODFCTSRCE", FK_COLUMNS="DEFECT_CD""DFCT_SOURCE_CD" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(DEFECT_CD) OR
    UPDATE(DFCT_SOURCE_CD)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,DEFECT_SEVERITY_TIER
      WHERE
        /*  %JoinFKPK(DEFECT_SEVERITY_TIER,deleted," = "," AND") */
        DEFECT_SEVERITY_TIER.DEFECT_CD = deleted.DEFECT_CD AND
        DEFECT_SEVERITY_TIER.DFCT_SOURCE_CD = deleted.DFCT_SOURCE_CD
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update DEFECT_SOURCE because DEFECT_SEVERITY_TIER exists.'
      GOTO error
    END
  END


  /* ERwin Builtin Trigger */
  RETURN
error:
    raiserror (@errmsg,16,-1)
    rollback transaction
END

go




CREATE TRIGGER tD_DICT_METADATA_ENTITY ON DICT_METADATA_ENTITY FOR DELETE AS
/* ERwin Builtin Trigger */
/* DELETE trigger on DICT_METADATA_ENTITY */
BEGIN
  DECLARE  @errno   int,
           @errmsg  varchar(255)
    /* ERwin Builtin Trigger */
    /* DICT_METADATA_ENTITY  SCORING_MODEL_FACTORS on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00049d13", PARENT_OWNER="", PARENT_TABLE="DICT_METADATA_ENTITY"
    CHILD_OWNER="", CHILD_TABLE="SCORING_MODEL_FACTORS"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="SCORMODLFACTTOMETAENTITY", FK_COLUMNS="ENTITY_NAME" */
    IF EXISTS (
      SELECT * FROM deleted,SCORING_MODEL_FACTORS
      WHERE
        /*  %JoinFKPK(SCORING_MODEL_FACTORS,deleted," = "," AND") */
        SCORING_MODEL_FACTORS.ENTITY_NAME = deleted.ENTITY_NAME
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete DICT_METADATA_ENTITY because SCORING_MODEL_FACTORS exists.'
      GOTO error
    END

    /* ERwin Builtin Trigger */
    /* DICT_METADATA_ENTITY  QATREE_QSTN_CAPTURE on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="DICT_METADATA_ENTITY"
    CHILD_OWNER="", CHILD_TABLE="QATREE_QSTN_CAPTURE"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="QATREEQSTNCAPTTOMETAENTITY", FK_COLUMNS="ENTITY_NAME" */
    IF EXISTS (
      SELECT * FROM deleted,QATREE_QSTN_CAPTURE
      WHERE
        /*  %JoinFKPK(QATREE_QSTN_CAPTURE,deleted," = "," AND") */
        QATREE_QSTN_CAPTURE.ENTITY_NAME = deleted.ENTITY_NAME
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete DICT_METADATA_ENTITY because QATREE_QSTN_CAPTURE exists.'
      GOTO error
    END

    /* ERwin Builtin Trigger */
    /* DICT_METADATA_ENTITY  QATREE_QSTN_CONDITION on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="DICT_METADATA_ENTITY"
    CHILD_OWNER="", CHILD_TABLE="QATREE_QSTN_CONDITION"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="QATREEQSTNCONDTOMETAENTITY", FK_COLUMNS="ENTITY_NAME" */
    IF EXISTS (
      SELECT * FROM deleted,QATREE_QSTN_CONDITION
      WHERE
        /*  %JoinFKPK(QATREE_QSTN_CONDITION,deleted," = "," AND") */
        QATREE_QSTN_CONDITION.ENTITY_NAME = deleted.ENTITY_NAME
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete DICT_METADATA_ENTITY because QATREE_QSTN_CONDITION exists.'
      GOTO error
    END

    /* ERwin Builtin Trigger */
    /* DICT_METADATA_ENTITY  DICT_METADATA_FIELD on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="DICT_METADATA_ENTITY"
    CHILD_OWNER="", CHILD_TABLE="DICT_METADATA_FIELD"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="METAFIELDTOMETAENTITY", FK_COLUMNS="ENTITY_NAME" */
    IF EXISTS (
      SELECT * FROM deleted,DICT_METADATA_FIELD
      WHERE
        /*  %JoinFKPK(DICT_METADATA_FIELD,deleted," = "," AND") */
        DICT_METADATA_FIELD.ENTITY_NAME = deleted.ENTITY_NAME
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete DICT_METADATA_ENTITY because DICT_METADATA_FIELD exists.'
      GOTO error
    END


    /* ERwin Builtin Trigger */
    RETURN
error:
    raiserror (@errmsg,16,-1)
    rollback transaction
END

go


CREATE TRIGGER tU_DICT_METADATA_ENTITY ON DICT_METADATA_ENTITY FOR UPDATE AS
/* ERwin Builtin Trigger */
/* UPDATE trigger on DICT_METADATA_ENTITY */
BEGIN
  DECLARE  @numrows int,
           @nullcnt int,
           @validcnt int,
           @insENTITY_NAME varchar(60),
           @errno   int,
           @errmsg  varchar(255)

  SELECT @numrows = @@rowcount
  /* ERwin Builtin Trigger */
  /* DICT_METADATA_ENTITY  SCORING_MODEL_FACTORS on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="00052097", PARENT_OWNER="", PARENT_TABLE="DICT_METADATA_ENTITY"
    CHILD_OWNER="", CHILD_TABLE="SCORING_MODEL_FACTORS"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="SCORMODLFACTTOMETAENTITY", FK_COLUMNS="ENTITY_NAME" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(ENTITY_NAME)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,SCORING_MODEL_FACTORS
      WHERE
        /*  %JoinFKPK(SCORING_MODEL_FACTORS,deleted," = "," AND") */
        SCORING_MODEL_FACTORS.ENTITY_NAME = deleted.ENTITY_NAME
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update DICT_METADATA_ENTITY because SCORING_MODEL_FACTORS exists.'
      GOTO error
    END
  END

  /* ERwin Builtin Trigger */
  /* DICT_METADATA_ENTITY  QATREE_QSTN_CAPTURE on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="DICT_METADATA_ENTITY"
    CHILD_OWNER="", CHILD_TABLE="QATREE_QSTN_CAPTURE"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="QATREEQSTNCAPTTOMETAENTITY", FK_COLUMNS="ENTITY_NAME" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(ENTITY_NAME)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,QATREE_QSTN_CAPTURE
      WHERE
        /*  %JoinFKPK(QATREE_QSTN_CAPTURE,deleted," = "," AND") */
        QATREE_QSTN_CAPTURE.ENTITY_NAME = deleted.ENTITY_NAME
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update DICT_METADATA_ENTITY because QATREE_QSTN_CAPTURE exists.'
      GOTO error
    END
  END

  /* ERwin Builtin Trigger */
  /* DICT_METADATA_ENTITY  QATREE_QSTN_CONDITION on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="DICT_METADATA_ENTITY"
    CHILD_OWNER="", CHILD_TABLE="QATREE_QSTN_CONDITION"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="QATREEQSTNCONDTOMETAENTITY", FK_COLUMNS="ENTITY_NAME" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(ENTITY_NAME)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,QATREE_QSTN_CONDITION
      WHERE
        /*  %JoinFKPK(QATREE_QSTN_CONDITION,deleted," = "," AND") */
        QATREE_QSTN_CONDITION.ENTITY_NAME = deleted.ENTITY_NAME
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update DICT_METADATA_ENTITY because QATREE_QSTN_CONDITION exists.'
      GOTO error
    END
  END

  /* ERwin Builtin Trigger */
  /* DICT_METADATA_ENTITY  DICT_METADATA_FIELD on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="DICT_METADATA_ENTITY"
    CHILD_OWNER="", CHILD_TABLE="DICT_METADATA_FIELD"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="METAFIELDTOMETAENTITY", FK_COLUMNS="ENTITY_NAME" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(ENTITY_NAME)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,DICT_METADATA_FIELD
      WHERE
        /*  %JoinFKPK(DICT_METADATA_FIELD,deleted," = "," AND") */
        DICT_METADATA_FIELD.ENTITY_NAME = deleted.ENTITY_NAME
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update DICT_METADATA_ENTITY because DICT_METADATA_FIELD exists.'
      GOTO error
    END
  END


  /* ERwin Builtin Trigger */
  RETURN
error:
    raiserror (@errmsg,16,-1)
    rollback transaction
END

go




CREATE TRIGGER tD_DICT_METADATA_FIELD ON DICT_METADATA_FIELD FOR DELETE AS
/* ERwin Builtin Trigger */
/* DELETE trigger on DICT_METADATA_FIELD */
BEGIN
  DECLARE  @errno   int,
           @errmsg  varchar(255)
    /* ERwin Builtin Trigger */
    /* DICT_METADATA_FIELD  SCORING_MODEL_FACTORS on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="0003d571", PARENT_OWNER="", PARENT_TABLE="DICT_METADATA_FIELD"
    CHILD_OWNER="", CHILD_TABLE="SCORING_MODEL_FACTORS"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="SCORMODLFACTTOMETAFIELD", FK_COLUMNS="ENTITY_NAME""FIELD_NAME" */
    IF EXISTS (
      SELECT * FROM deleted,SCORING_MODEL_FACTORS
      WHERE
        /*  %JoinFKPK(SCORING_MODEL_FACTORS,deleted," = "," AND") */
        SCORING_MODEL_FACTORS.ENTITY_NAME = deleted.ENTITY_NAME AND
        SCORING_MODEL_FACTORS.FIELD_NAME = deleted.FIELD_NAME
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete DICT_METADATA_FIELD because SCORING_MODEL_FACTORS exists.'
      GOTO error
    END

    /* ERwin Builtin Trigger */
    /* DICT_METADATA_FIELD  QATREE_QSTN_CAPTURE on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="DICT_METADATA_FIELD"
    CHILD_OWNER="", CHILD_TABLE="QATREE_QSTN_CAPTURE"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="QATREEQSTNCAPTTOMETAFIELD", FK_COLUMNS="ENTITY_NAME""FIELD_NAME" */
    IF EXISTS (
      SELECT * FROM deleted,QATREE_QSTN_CAPTURE
      WHERE
        /*  %JoinFKPK(QATREE_QSTN_CAPTURE,deleted," = "," AND") */
        QATREE_QSTN_CAPTURE.ENTITY_NAME = deleted.ENTITY_NAME AND
        QATREE_QSTN_CAPTURE.FIELD_NAME = deleted.FIELD_NAME
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete DICT_METADATA_FIELD because QATREE_QSTN_CAPTURE exists.'
      GOTO error
    END

    /* ERwin Builtin Trigger */
    /* DICT_METADATA_FIELD  QATREE_QSTN_CONDITION on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="DICT_METADATA_FIELD"
    CHILD_OWNER="", CHILD_TABLE="QATREE_QSTN_CONDITION"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="QATREEQSTNCONDTOMETAFIELD", FK_COLUMNS="ENTITY_NAME""FIELD_NAME" */
    IF EXISTS (
      SELECT * FROM deleted,QATREE_QSTN_CONDITION
      WHERE
        /*  %JoinFKPK(QATREE_QSTN_CONDITION,deleted," = "," AND") */
        QATREE_QSTN_CONDITION.ENTITY_NAME = deleted.ENTITY_NAME AND
        QATREE_QSTN_CONDITION.FIELD_NAME = deleted.FIELD_NAME
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete DICT_METADATA_FIELD because QATREE_QSTN_CONDITION exists.'
      GOTO error
    END


    /* ERwin Builtin Trigger */
    RETURN
error:
    raiserror (@errmsg,16,-1)
    rollback transaction
END

go


CREATE TRIGGER tU_DICT_METADATA_FIELD ON DICT_METADATA_FIELD FOR UPDATE AS
/* ERwin Builtin Trigger */
/* UPDATE trigger on DICT_METADATA_FIELD */
BEGIN
  DECLARE  @numrows int,
           @nullcnt int,
           @validcnt int,
           @insENTITY_NAME character varying(60), 
           @insFIELD_NAME varchar(60),
           @errno   int,
           @errmsg  varchar(255)

  SELECT @numrows = @@rowcount
  /* ERwin Builtin Trigger */
  /* DICT_METADATA_FIELD  SCORING_MODEL_FACTORS on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="000459d7", PARENT_OWNER="", PARENT_TABLE="DICT_METADATA_FIELD"
    CHILD_OWNER="", CHILD_TABLE="SCORING_MODEL_FACTORS"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="SCORMODLFACTTOMETAFIELD", FK_COLUMNS="ENTITY_NAME""FIELD_NAME" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(ENTITY_NAME) OR
    UPDATE(FIELD_NAME)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,SCORING_MODEL_FACTORS
      WHERE
        /*  %JoinFKPK(SCORING_MODEL_FACTORS,deleted," = "," AND") */
        SCORING_MODEL_FACTORS.ENTITY_NAME = deleted.ENTITY_NAME AND
        SCORING_MODEL_FACTORS.FIELD_NAME = deleted.FIELD_NAME
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update DICT_METADATA_FIELD because SCORING_MODEL_FACTORS exists.'
      GOTO error
    END
  END

  /* ERwin Builtin Trigger */
  /* DICT_METADATA_FIELD  QATREE_QSTN_CAPTURE on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="DICT_METADATA_FIELD"
    CHILD_OWNER="", CHILD_TABLE="QATREE_QSTN_CAPTURE"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="QATREEQSTNCAPTTOMETAFIELD", FK_COLUMNS="ENTITY_NAME""FIELD_NAME" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(ENTITY_NAME) OR
    UPDATE(FIELD_NAME)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,QATREE_QSTN_CAPTURE
      WHERE
        /*  %JoinFKPK(QATREE_QSTN_CAPTURE,deleted," = "," AND") */
        QATREE_QSTN_CAPTURE.ENTITY_NAME = deleted.ENTITY_NAME AND
        QATREE_QSTN_CAPTURE.FIELD_NAME = deleted.FIELD_NAME
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update DICT_METADATA_FIELD because QATREE_QSTN_CAPTURE exists.'
      GOTO error
    END
  END

  /* ERwin Builtin Trigger */
  /* DICT_METADATA_FIELD  QATREE_QSTN_CONDITION on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="DICT_METADATA_FIELD"
    CHILD_OWNER="", CHILD_TABLE="QATREE_QSTN_CONDITION"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="QATREEQSTNCONDTOMETAFIELD", FK_COLUMNS="ENTITY_NAME""FIELD_NAME" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(ENTITY_NAME) OR
    UPDATE(FIELD_NAME)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,QATREE_QSTN_CONDITION
      WHERE
        /*  %JoinFKPK(QATREE_QSTN_CONDITION,deleted," = "," AND") */
        QATREE_QSTN_CONDITION.ENTITY_NAME = deleted.ENTITY_NAME AND
        QATREE_QSTN_CONDITION.FIELD_NAME = deleted.FIELD_NAME
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update DICT_METADATA_FIELD because QATREE_QSTN_CONDITION exists.'
      GOTO error
    END
  END


  /* ERwin Builtin Trigger */
  RETURN
error:
    raiserror (@errmsg,16,-1)
    rollback transaction
END

go




CREATE TRIGGER tD_RATING ON RATING FOR DELETE AS
/* ERwin Builtin Trigger */
/* DELETE trigger on RATING */
BEGIN
  DECLARE  @errno   int,
           @errmsg  varchar(255)
    /* ERwin Builtin Trigger */
    /* RATING  DEFECT_SEVERITY_TIER on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00011515", PARENT_OWNER="", PARENT_TABLE="RATING"
    CHILD_OWNER="", CHILD_TABLE="DEFECT_SEVERITY_TIER"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="DFCTSEVERITYTIERTORATING", FK_COLUMNS="RATING_CD" */
    IF EXISTS (
      SELECT * FROM deleted,DEFECT_SEVERITY_TIER
      WHERE
        /*  %JoinFKPK(DEFECT_SEVERITY_TIER,deleted," = "," AND") */
        DEFECT_SEVERITY_TIER.RATING_CD = deleted.RATING_CD
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete RATING because DEFECT_SEVERITY_TIER exists.'
      GOTO error
    END


    /* ERwin Builtin Trigger */
    RETURN
error:
    raiserror (@errmsg,16,-1)
    rollback transaction
END

go


CREATE TRIGGER tU_RATING ON RATING FOR UPDATE AS
/* ERwin Builtin Trigger */
/* UPDATE trigger on RATING */
BEGIN
  DECLARE  @numrows int,
           @nullcnt int,
           @validcnt int,
           @insRATING_CD varchar(16),
           @errno   int,
           @errmsg  varchar(255)

  SELECT @numrows = @@rowcount
  /* ERwin Builtin Trigger */
  /* RATING  DEFECT_SEVERITY_TIER on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="000123a1", PARENT_OWNER="", PARENT_TABLE="RATING"
    CHILD_OWNER="", CHILD_TABLE="DEFECT_SEVERITY_TIER"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="DFCTSEVERITYTIERTORATING", FK_COLUMNS="RATING_CD" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(RATING_CD)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,DEFECT_SEVERITY_TIER
      WHERE
        /*  %JoinFKPK(DEFECT_SEVERITY_TIER,deleted," = "," AND") */
        DEFECT_SEVERITY_TIER.RATING_CD = deleted.RATING_CD
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update RATING because DEFECT_SEVERITY_TIER exists.'
      GOTO error
    END
  END


  /* ERwin Builtin Trigger */
  RETURN
error:
    raiserror (@errmsg,16,-1)
    rollback transaction
END

go




CREATE TRIGGER tD_LOAN_SELECTION ON LOAN_SELECTION FOR DELETE AS
/* ERwin Builtin Trigger */
/* DELETE trigger on LOAN_SELECTION */
BEGIN
  DECLARE  @errno   int,
           @errmsg  varchar(255)
    /* ERwin Builtin Trigger */
    /* LOAN_SELECTION  REVIEW on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00023c37", PARENT_OWNER="", PARENT_TABLE="LOAN_SELECTION"
    CHILD_OWNER="", CHILD_TABLE="REVIEW"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="REVIEWSELECTION", FK_COLUMNS="SELECTION_ID" */
    IF EXISTS (
      SELECT * FROM deleted,REVIEW
      WHERE
        /*  %JoinFKPK(REVIEW,deleted," = "," AND") */
        REVIEW.SELECTION_ID = deleted.SELECTION_ID
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete LOAN_SELECTION because REVIEW exists.'
      GOTO error
    END

    /* ERwin Builtin Trigger */
    /* LOAN_SELECTION  LOAN_SELECTION_CASE_SUMMARY on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="LOAN_SELECTION"
    CHILD_OWNER="", CHILD_TABLE="LOAN_SELECTION_CASE_SUMMARY"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="CASESUMMARYTOSELECTION", FK_COLUMNS="SELECTION_ID" */
    IF EXISTS (
      SELECT * FROM deleted,LOAN_SELECTION_CASE_SUMMARY
      WHERE
        /*  %JoinFKPK(LOAN_SELECTION_CASE_SUMMARY,deleted," = "," AND") */
        LOAN_SELECTION_CASE_SUMMARY.SELECTION_ID = deleted.SELECTION_ID
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete LOAN_SELECTION because LOAN_SELECTION_CASE_SUMMARY exists.'
      GOTO error
    END


    /* ERwin Builtin Trigger */
    RETURN
error:
    raiserror (@errmsg,16,-1)
    rollback transaction
END

go


CREATE TRIGGER tU_LOAN_SELECTION ON LOAN_SELECTION FOR UPDATE AS
/* ERwin Builtin Trigger */
/* UPDATE trigger on LOAN_SELECTION */
BEGIN
  DECLARE  @numrows int,
           @nullcnt int,
           @validcnt int,
           @insSELECTION_ID CHAR(16),
           @errno   int,
           @errmsg  varchar(255)

  SELECT @numrows = @@rowcount
  /* ERwin Builtin Trigger */
  /* LOAN_SELECTION  REVIEW on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="00027842", PARENT_OWNER="", PARENT_TABLE="LOAN_SELECTION"
    CHILD_OWNER="", CHILD_TABLE="REVIEW"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="REVIEWSELECTION", FK_COLUMNS="SELECTION_ID" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(SELECTION_ID)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,REVIEW
      WHERE
        /*  %JoinFKPK(REVIEW,deleted," = "," AND") */
        REVIEW.SELECTION_ID = deleted.SELECTION_ID
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update LOAN_SELECTION because REVIEW exists.'
      GOTO error
    END
  END

  /* ERwin Builtin Trigger */
  /* LOAN_SELECTION  LOAN_SELECTION_CASE_SUMMARY on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="LOAN_SELECTION"
    CHILD_OWNER="", CHILD_TABLE="LOAN_SELECTION_CASE_SUMMARY"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="CASESUMMARYTOSELECTION", FK_COLUMNS="SELECTION_ID" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(SELECTION_ID)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,LOAN_SELECTION_CASE_SUMMARY
      WHERE
        /*  %JoinFKPK(LOAN_SELECTION_CASE_SUMMARY,deleted," = "," AND") */
        LOAN_SELECTION_CASE_SUMMARY.SELECTION_ID = deleted.SELECTION_ID
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update LOAN_SELECTION because LOAN_SELECTION_CASE_SUMMARY exists.'
      GOTO error
    END
  END


  /* ERwin Builtin Trigger */
  RETURN
error:
    raiserror (@errmsg,16,-1)
    rollback transaction
END

go




CREATE TRIGGER tD_PERSONNEL ON PERSONNEL FOR DELETE AS
/* ERwin Builtin Trigger */
/* DELETE trigger on PERSONNEL */
BEGIN
  DECLARE  @errno   int,
           @errmsg  varchar(255)
    /* ERwin Builtin Trigger */
    /* PERSONNEL  RVW_BATCH_PRSNNL on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00024378", PARENT_OWNER="", PARENT_TABLE="PERSONNEL"
    CHILD_OWNER="", CHILD_TABLE="RVW_BATCH_PRSNNL"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="REVIEWBATCHTOPERSONNEL", FK_COLUMNS="PRSNNL_ID" */
    IF EXISTS (
      SELECT * FROM deleted,RVW_BATCH_PRSNNL
      WHERE
        /*  %JoinFKPK(RVW_BATCH_PRSNNL,deleted," = "," AND") */
        RVW_BATCH_PRSNNL.PRSNNL_ID = deleted.PRSNNL_ID
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete PERSONNEL because RVW_BATCH_PRSNNL exists.'
      GOTO error
    END

    /* ERwin Builtin Trigger */
    /* PERSONNEL  PERSONNEL_ASSIGNMENT_TYPE on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="PERSONNEL"
    CHILD_OWNER="", CHILD_TABLE="PERSONNEL_ASSIGNMENT_TYPE"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="PERSONASSIGNTYPESPERSON", FK_COLUMNS="PRSNNL_ID" */
    IF EXISTS (
      SELECT * FROM deleted,PERSONNEL_ASSIGNMENT_TYPE
      WHERE
        /*  %JoinFKPK(PERSONNEL_ASSIGNMENT_TYPE,deleted," = "," AND") */
        PERSONNEL_ASSIGNMENT_TYPE.PRSNNL_ID = deleted.PRSNNL_ID
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete PERSONNEL because PERSONNEL_ASSIGNMENT_TYPE exists.'
      GOTO error
    END


    /* ERwin Builtin Trigger */
    RETURN
error:
    raiserror (@errmsg,16,-1)
    rollback transaction
END

go


CREATE TRIGGER tU_PERSONNEL ON PERSONNEL FOR UPDATE AS
/* ERwin Builtin Trigger */
/* UPDATE trigger on PERSONNEL */
BEGIN
  DECLARE  @numrows int,
           @nullcnt int,
           @validcnt int,
           @insPRSNNL_ID varchar(32),
           @errno   int,
           @errmsg  varchar(255)

  SELECT @numrows = @@rowcount
  /* ERwin Builtin Trigger */
  /* PERSONNEL  RVW_BATCH_PRSNNL on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="00026a8b", PARENT_OWNER="", PARENT_TABLE="PERSONNEL"
    CHILD_OWNER="", CHILD_TABLE="RVW_BATCH_PRSNNL"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="REVIEWBATCHTOPERSONNEL", FK_COLUMNS="PRSNNL_ID" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(PRSNNL_ID)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,RVW_BATCH_PRSNNL
      WHERE
        /*  %JoinFKPK(RVW_BATCH_PRSNNL,deleted," = "," AND") */
        RVW_BATCH_PRSNNL.PRSNNL_ID = deleted.PRSNNL_ID
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update PERSONNEL because RVW_BATCH_PRSNNL exists.'
      GOTO error
    END
  END

  /* ERwin Builtin Trigger */
  /* PERSONNEL  PERSONNEL_ASSIGNMENT_TYPE on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="PERSONNEL"
    CHILD_OWNER="", CHILD_TABLE="PERSONNEL_ASSIGNMENT_TYPE"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="PERSONASSIGNTYPESPERSON", FK_COLUMNS="PRSNNL_ID" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(PRSNNL_ID)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,PERSONNEL_ASSIGNMENT_TYPE
      WHERE
        /*  %JoinFKPK(PERSONNEL_ASSIGNMENT_TYPE,deleted," = "," AND") */
        PERSONNEL_ASSIGNMENT_TYPE.PRSNNL_ID = deleted.PRSNNL_ID
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update PERSONNEL because PERSONNEL_ASSIGNMENT_TYPE exists.'
      GOTO error
    END
  END


  /* ERwin Builtin Trigger */
  RETURN
error:
    raiserror (@errmsg,16,-1)
    rollback transaction
END

go




CREATE TRIGGER tD_QATREE ON QATREE FOR DELETE AS
/* ERwin Builtin Trigger */
/* DELETE trigger on QATREE */
BEGIN
  DECLARE  @errno   int,
           @errmsg  varchar(255)
    /* ERwin Builtin Trigger */
    /* QATREE  QATREE_QSTN_CAPTURE on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="000419df", PARENT_OWNER="", PARENT_TABLE="QATREE"
    CHILD_OWNER="", CHILD_TABLE="QATREE_QSTN_CAPTURE"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="QATREEQSTNCAPTTOQATREE", FK_COLUMNS="QATREE_ID" */
    IF EXISTS (
      SELECT * FROM deleted,QATREE_QSTN_CAPTURE
      WHERE
        /*  %JoinFKPK(QATREE_QSTN_CAPTURE,deleted," = "," AND") */
        QATREE_QSTN_CAPTURE.QATREE_ID = deleted.QATREE_ID
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete QATREE because QATREE_QSTN_CAPTURE exists.'
      GOTO error
    END

    /* ERwin Builtin Trigger */
    /* QATREE  QATREE_QSTN_CONDITION on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="QATREE"
    CHILD_OWNER="", CHILD_TABLE="QATREE_QSTN_CONDITION"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="QATREEQSTNCONDTOQATREE", FK_COLUMNS="QATREE_ID" */
    IF EXISTS (
      SELECT * FROM deleted,QATREE_QSTN_CONDITION
      WHERE
        /*  %JoinFKPK(QATREE_QSTN_CONDITION,deleted," = "," AND") */
        QATREE_QSTN_CONDITION.QATREE_ID = deleted.QATREE_ID
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete QATREE because QATREE_QSTN_CONDITION exists.'
      GOTO error
    END

    /* ERwin Builtin Trigger */
    /* QATREE  QATREE_QUESTION on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="QATREE"
    CHILD_OWNER="", CHILD_TABLE="QATREE_QUESTION"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="QUESTIONTOQATREE", FK_COLUMNS="QATREE_ID" */
    IF EXISTS (
      SELECT * FROM deleted,QATREE_QUESTION
      WHERE
        /*  %JoinFKPK(QATREE_QUESTION,deleted," = "," AND") */
        QATREE_QUESTION.QATREE_ID = deleted.QATREE_ID
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete QATREE because QATREE_QUESTION exists.'
      GOTO error
    END

    /* ERwin Builtin Trigger */
    /* QATREE  QATREE_BRANCH on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="QATREE"
    CHILD_OWNER="", CHILD_TABLE="QATREE_BRANCH"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="QATREEBRANCHTOQATREE", FK_COLUMNS="QATREE_ID" */
    IF EXISTS (
      SELECT * FROM deleted,QATREE_BRANCH
      WHERE
        /*  %JoinFKPK(QATREE_BRANCH,deleted," = "," AND") */
        QATREE_BRANCH.QATREE_ID = deleted.QATREE_ID
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete QATREE because QATREE_BRANCH exists.'
      GOTO error
    END


    /* ERwin Builtin Trigger */
    RETURN
error:
    raiserror (@errmsg,16,-1)
    rollback transaction
END

go


CREATE TRIGGER tU_QATREE ON QATREE FOR UPDATE AS
/* ERwin Builtin Trigger */
/* UPDATE trigger on QATREE */
BEGIN
  DECLARE  @numrows int,
           @nullcnt int,
           @validcnt int,
           @insQATREE_ID varchar(16),
           @errno   int,
           @errmsg  varchar(255)

  SELECT @numrows = @@rowcount
  /* ERwin Builtin Trigger */
  /* QATREE  QATREE_QSTN_CAPTURE on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="0004816a", PARENT_OWNER="", PARENT_TABLE="QATREE"
    CHILD_OWNER="", CHILD_TABLE="QATREE_QSTN_CAPTURE"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="QATREEQSTNCAPTTOQATREE", FK_COLUMNS="QATREE_ID" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(QATREE_ID)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,QATREE_QSTN_CAPTURE
      WHERE
        /*  %JoinFKPK(QATREE_QSTN_CAPTURE,deleted," = "," AND") */
        QATREE_QSTN_CAPTURE.QATREE_ID = deleted.QATREE_ID
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update QATREE because QATREE_QSTN_CAPTURE exists.'
      GOTO error
    END
  END

  /* ERwin Builtin Trigger */
  /* QATREE  QATREE_QSTN_CONDITION on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="QATREE"
    CHILD_OWNER="", CHILD_TABLE="QATREE_QSTN_CONDITION"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="QATREEQSTNCONDTOQATREE", FK_COLUMNS="QATREE_ID" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(QATREE_ID)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,QATREE_QSTN_CONDITION
      WHERE
        /*  %JoinFKPK(QATREE_QSTN_CONDITION,deleted," = "," AND") */
        QATREE_QSTN_CONDITION.QATREE_ID = deleted.QATREE_ID
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update QATREE because QATREE_QSTN_CONDITION exists.'
      GOTO error
    END
  END

  /* ERwin Builtin Trigger */
  /* QATREE  QATREE_QUESTION on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="QATREE"
    CHILD_OWNER="", CHILD_TABLE="QATREE_QUESTION"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="QUESTIONTOQATREE", FK_COLUMNS="QATREE_ID" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(QATREE_ID)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,QATREE_QUESTION
      WHERE
        /*  %JoinFKPK(QATREE_QUESTION,deleted," = "," AND") */
        QATREE_QUESTION.QATREE_ID = deleted.QATREE_ID
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update QATREE because QATREE_QUESTION exists.'
      GOTO error
    END
  END

  /* ERwin Builtin Trigger */
  /* QATREE  QATREE_BRANCH on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="QATREE"
    CHILD_OWNER="", CHILD_TABLE="QATREE_BRANCH"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="QATREEBRANCHTOQATREE", FK_COLUMNS="QATREE_ID" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(QATREE_ID)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,QATREE_BRANCH
      WHERE
        /*  %JoinFKPK(QATREE_BRANCH,deleted," = "," AND") */
        QATREE_BRANCH.QATREE_ID = deleted.QATREE_ID
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update QATREE because QATREE_BRANCH exists.'
      GOTO error
    END
  END


  /* ERwin Builtin Trigger */
  RETURN
error:
    raiserror (@errmsg,16,-1)
    rollback transaction
END

go




CREATE TRIGGER tD_QATREE_OUTCOME ON QATREE_OUTCOME FOR DELETE AS
/* ERwin Builtin Trigger */
/* DELETE trigger on QATREE_OUTCOME */
BEGIN
  DECLARE  @errno   int,
           @errmsg  varchar(255)
    /* ERwin Builtin Trigger */
    /* QATREE_OUTCOME  QATREE_OUTCM_TIERS on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="0003539a", PARENT_OWNER="", PARENT_TABLE="QATREE_OUTCOME"
    CHILD_OWNER="", CHILD_TABLE="QATREE_OUTCM_TIERS"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="QATREEOUTCOMETOTIERS", FK_COLUMNS="QA_OUTCM_ID" */
    IF EXISTS (
      SELECT * FROM deleted,QATREE_OUTCM_TIERS
      WHERE
        /*  %JoinFKPK(QATREE_OUTCM_TIERS,deleted," = "," AND") */
        QATREE_OUTCM_TIERS.QA_OUTCM_ID = deleted.QA_OUTCM_ID
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete QATREE_OUTCOME because QATREE_OUTCM_TIERS exists.'
      GOTO error
    END

    /* ERwin Builtin Trigger */
    /* QATREE_OUTCOME  QATREE_OUTCM_SOURCES on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="QATREE_OUTCOME"
    CHILD_OWNER="", CHILD_TABLE="QATREE_OUTCM_SOURCES"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="QATREEOCSRCESTOOUTCOME", FK_COLUMNS="QA_OUTCM_ID" */
    IF EXISTS (
      SELECT * FROM deleted,QATREE_OUTCM_SOURCES
      WHERE
        /*  %JoinFKPK(QATREE_OUTCM_SOURCES,deleted," = "," AND") */
        QATREE_OUTCM_SOURCES.QA_OUTCM_ID = deleted.QA_OUTCM_ID
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete QATREE_OUTCOME because QATREE_OUTCM_SOURCES exists.'
      GOTO error
    END

    /* ERwin Builtin Trigger */
    /* QATREE_OUTCOME  QATREE_OUTCM_CAUSES on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="QATREE_OUTCOME"
    CHILD_OWNER="", CHILD_TABLE="QATREE_OUTCM_CAUSES"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="QATREEOCCAUSETOOUTCOME", FK_COLUMNS="QA_OUTCM_ID" */
    IF EXISTS (
      SELECT * FROM deleted,QATREE_OUTCM_CAUSES
      WHERE
        /*  %JoinFKPK(QATREE_OUTCM_CAUSES,deleted," = "," AND") */
        QATREE_OUTCM_CAUSES.QA_OUTCM_ID = deleted.QA_OUTCM_ID
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete QATREE_OUTCOME because QATREE_OUTCM_CAUSES exists.'
      GOTO error
    END


    /* ERwin Builtin Trigger */
    RETURN
error:
    raiserror (@errmsg,16,-1)
    rollback transaction
END

go


CREATE TRIGGER tU_QATREE_OUTCOME ON QATREE_OUTCOME FOR UPDATE AS
/* ERwin Builtin Trigger */
/* UPDATE trigger on QATREE_OUTCOME */
BEGIN
  DECLARE  @numrows int,
           @nullcnt int,
           @validcnt int,
           @insQA_OUTCM_ID varchar(16),
           @errno   int,
           @errmsg  varchar(255)

  SELECT @numrows = @@rowcount
  /* ERwin Builtin Trigger */
  /* QATREE_OUTCOME  QATREE_OUTCM_TIERS on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="0003b1f8", PARENT_OWNER="", PARENT_TABLE="QATREE_OUTCOME"
    CHILD_OWNER="", CHILD_TABLE="QATREE_OUTCM_TIERS"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="QATREEOUTCOMETOTIERS", FK_COLUMNS="QA_OUTCM_ID" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(QA_OUTCM_ID)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,QATREE_OUTCM_TIERS
      WHERE
        /*  %JoinFKPK(QATREE_OUTCM_TIERS,deleted," = "," AND") */
        QATREE_OUTCM_TIERS.QA_OUTCM_ID = deleted.QA_OUTCM_ID
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update QATREE_OUTCOME because QATREE_OUTCM_TIERS exists.'
      GOTO error
    END
  END

  /* ERwin Builtin Trigger */
  /* QATREE_OUTCOME  QATREE_OUTCM_SOURCES on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="QATREE_OUTCOME"
    CHILD_OWNER="", CHILD_TABLE="QATREE_OUTCM_SOURCES"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="QATREEOCSRCESTOOUTCOME", FK_COLUMNS="QA_OUTCM_ID" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(QA_OUTCM_ID)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,QATREE_OUTCM_SOURCES
      WHERE
        /*  %JoinFKPK(QATREE_OUTCM_SOURCES,deleted," = "," AND") */
        QATREE_OUTCM_SOURCES.QA_OUTCM_ID = deleted.QA_OUTCM_ID
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update QATREE_OUTCOME because QATREE_OUTCM_SOURCES exists.'
      GOTO error
    END
  END

  /* ERwin Builtin Trigger */
  /* QATREE_OUTCOME  QATREE_OUTCM_CAUSES on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="QATREE_OUTCOME"
    CHILD_OWNER="", CHILD_TABLE="QATREE_OUTCM_CAUSES"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="QATREEOCCAUSETOOUTCOME", FK_COLUMNS="QA_OUTCM_ID" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(QA_OUTCM_ID)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,QATREE_OUTCM_CAUSES
      WHERE
        /*  %JoinFKPK(QATREE_OUTCM_CAUSES,deleted," = "," AND") */
        QATREE_OUTCM_CAUSES.QA_OUTCM_ID = deleted.QA_OUTCM_ID
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update QATREE_OUTCOME because QATREE_OUTCM_CAUSES exists.'
      GOTO error
    END
  END


  /* ERwin Builtin Trigger */
  RETURN
error:
    raiserror (@errmsg,16,-1)
    rollback transaction
END

go




CREATE TRIGGER tD_QATREE_QUESTION ON QATREE_QUESTION FOR DELETE AS
/* ERwin Builtin Trigger */
/* DELETE trigger on QATREE_QUESTION */
BEGIN
  DECLARE  @errno   int,
           @errmsg  varchar(255)
    /* ERwin Builtin Trigger */
    /* QATREE_QUESTION  QATREE_QSTN_CAPTURE on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00028312", PARENT_OWNER="", PARENT_TABLE="QATREE_QUESTION"
    CHILD_OWNER="", CHILD_TABLE="QATREE_QSTN_CAPTURE"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="QATREEQSTNCAPTTOQAQSTN", FK_COLUMNS="QATREE_ID""QUESTION_ID" */
    IF EXISTS (
      SELECT * FROM deleted,QATREE_QSTN_CAPTURE
      WHERE
        /*  %JoinFKPK(QATREE_QSTN_CAPTURE,deleted," = "," AND") */
        QATREE_QSTN_CAPTURE.QATREE_ID = deleted.QUESTION_ID AND
        QATREE_QSTN_CAPTURE.QUESTION_ID = deleted.QATREE_ID
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete QATREE_QUESTION because QATREE_QSTN_CAPTURE exists.'
      GOTO error
    END

    /* ERwin Builtin Trigger */
    /* QATREE_QUESTION  QATREE_QSTN_CONDITION on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="QATREE_QUESTION"
    CHILD_OWNER="", CHILD_TABLE="QATREE_QSTN_CONDITION"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="QATREEQSTNCONDTOQAQSTN", FK_COLUMNS="QATREE_ID""QUESTION_ID" */
    IF EXISTS (
      SELECT * FROM deleted,QATREE_QSTN_CONDITION
      WHERE
        /*  %JoinFKPK(QATREE_QSTN_CONDITION,deleted," = "," AND") */
        QATREE_QSTN_CONDITION.QATREE_ID = deleted.QUESTION_ID AND
        QATREE_QSTN_CONDITION.QUESTION_ID = deleted.QATREE_ID
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete QATREE_QUESTION because QATREE_QSTN_CONDITION exists.'
      GOTO error
    END


    /* ERwin Builtin Trigger */
    RETURN
error:
    raiserror (@errmsg,16,-1)
    rollback transaction
END

go


CREATE TRIGGER tU_QATREE_QUESTION ON QATREE_QUESTION FOR UPDATE AS
/* ERwin Builtin Trigger */
/* UPDATE trigger on QATREE_QUESTION */
BEGIN
  DECLARE  @numrows int,
           @nullcnt int,
           @validcnt int,
           @insQUESTION_ID varchar(16), 
           @insQATREE_ID varchar(16),
           @errno   int,
           @errmsg  varchar(255)

  SELECT @numrows = @@rowcount
  /* ERwin Builtin Trigger */
  /* QATREE_QUESTION  QATREE_QSTN_CAPTURE on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="0002bdd1", PARENT_OWNER="", PARENT_TABLE="QATREE_QUESTION"
    CHILD_OWNER="", CHILD_TABLE="QATREE_QSTN_CAPTURE"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="QATREEQSTNCAPTTOQAQSTN", FK_COLUMNS="QATREE_ID""QUESTION_ID" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(QUESTION_ID) OR
    UPDATE(QATREE_ID)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,QATREE_QSTN_CAPTURE
      WHERE
        /*  %JoinFKPK(QATREE_QSTN_CAPTURE,deleted," = "," AND") */
        QATREE_QSTN_CAPTURE.QATREE_ID = deleted.QUESTION_ID AND
        QATREE_QSTN_CAPTURE.QUESTION_ID = deleted.QATREE_ID
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update QATREE_QUESTION because QATREE_QSTN_CAPTURE exists.'
      GOTO error
    END
  END

  /* ERwin Builtin Trigger */
  /* QATREE_QUESTION  QATREE_QSTN_CONDITION on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="QATREE_QUESTION"
    CHILD_OWNER="", CHILD_TABLE="QATREE_QSTN_CONDITION"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="QATREEQSTNCONDTOQAQSTN", FK_COLUMNS="QATREE_ID""QUESTION_ID" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(QUESTION_ID) OR
    UPDATE(QATREE_ID)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,QATREE_QSTN_CONDITION
      WHERE
        /*  %JoinFKPK(QATREE_QSTN_CONDITION,deleted," = "," AND") */
        QATREE_QSTN_CONDITION.QATREE_ID = deleted.QUESTION_ID AND
        QATREE_QSTN_CONDITION.QUESTION_ID = deleted.QATREE_ID
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update QATREE_QUESTION because QATREE_QSTN_CONDITION exists.'
      GOTO error
    END
  END


  /* ERwin Builtin Trigger */
  RETURN
error:
    raiserror (@errmsg,16,-1)
    rollback transaction
END

go




CREATE TRIGGER tD_REQUESTED_BINDER ON REQUESTED_BINDER FOR DELETE AS
/* ERwin Builtin Trigger */
/* DELETE trigger on REQUESTED_BINDER */
BEGIN
  DECLARE  @errno   int,
           @errmsg  varchar(255)
    /* ERwin Builtin Trigger */
    /* REQUESTED_BINDER  RQST_FROM_LENDER on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00011b08", PARENT_OWNER="", PARENT_TABLE="REQUESTED_BINDER"
    CHILD_OWNER="", CHILD_TABLE="RQST_FROM_LENDER"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="LENDBINDREQ", FK_COLUMNS="SLCTN_REQ_ID" */
    IF EXISTS (
      SELECT * FROM deleted,RQST_FROM_LENDER
      WHERE
        /*  %JoinFKPK(RQST_FROM_LENDER,deleted," = "," AND") */
        RQST_FROM_LENDER.SLCTN_REQ_ID = deleted.SLCTN_REQ_ID
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete REQUESTED_BINDER because RQST_FROM_LENDER exists.'
      GOTO error
    END


    /* ERwin Builtin Trigger */
    RETURN
error:
    raiserror (@errmsg,16,-1)
    rollback transaction
END

go


CREATE TRIGGER tU_REQUESTED_BINDER ON REQUESTED_BINDER FOR UPDATE AS
/* ERwin Builtin Trigger */
/* UPDATE trigger on REQUESTED_BINDER */
BEGIN
  DECLARE  @numrows int,
           @nullcnt int,
           @validcnt int,
           @insSLCTN_REQ_ID CHAR(36),
           @errno   int,
           @errmsg  varchar(255)

  SELECT @numrows = @@rowcount
  /* ERwin Builtin Trigger */
  /* REQUESTED_BINDER  RQST_FROM_LENDER on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="000138a5", PARENT_OWNER="", PARENT_TABLE="REQUESTED_BINDER"
    CHILD_OWNER="", CHILD_TABLE="RQST_FROM_LENDER"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="LENDBINDREQ", FK_COLUMNS="SLCTN_REQ_ID" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(SLCTN_REQ_ID)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,RQST_FROM_LENDER
      WHERE
        /*  %JoinFKPK(RQST_FROM_LENDER,deleted," = "," AND") */
        RQST_FROM_LENDER.SLCTN_REQ_ID = deleted.SLCTN_REQ_ID
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update REQUESTED_BINDER because RQST_FROM_LENDER exists.'
      GOTO error
    END
  END


  /* ERwin Builtin Trigger */
  RETURN
error:
    raiserror (@errmsg,16,-1)
    rollback transaction
END

go




CREATE TRIGGER tD_REVIEW ON REVIEW FOR DELETE AS
/* ERwin Builtin Trigger */
/* DELETE trigger on REVIEW */
BEGIN
  DECLARE  @errno   int,
           @errmsg  varchar(255)
    /* ERwin Builtin Trigger */
    /* REVIEW  RVW_LVL_DEFECT_AUD on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="0003098c", PARENT_OWNER="", PARENT_TABLE="REVIEW"
    CHILD_OWNER="", CHILD_TABLE="RVW_LVL_DEFECT_AUD"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="RVWLVLDEFAUDTOREVIEW", FK_COLUMNS="REVIEW_ID" */
    IF EXISTS (
      SELECT * FROM deleted,RVW_LVL_DEFECT_AUD
      WHERE
        /*  %JoinFKPK(RVW_LVL_DEFECT_AUD,deleted," = "," AND") */
        RVW_LVL_DEFECT_AUD.REVIEW_ID = deleted.REVIEW_ID
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete REVIEW because RVW_LVL_DEFECT_AUD exists.'
      GOTO error
    END

    /* ERwin Builtin Trigger */
    /* REVIEW  RVW_LVL_DEFECT on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="REVIEW"
    CHILD_OWNER="", CHILD_TABLE="RVW_LVL_DEFECT"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="REVIEWLEVELDEFECTTOREVIEW", FK_COLUMNS="REVIEW_ID" */
    IF EXISTS (
      SELECT * FROM deleted,RVW_LVL_DEFECT
      WHERE
        /*  %JoinFKPK(RVW_LVL_DEFECT,deleted," = "," AND") */
        RVW_LVL_DEFECT.REVIEW_ID = deleted.REVIEW_ID
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete REVIEW because RVW_LVL_DEFECT exists.'
      GOTO error
    END

    /* ERwin Builtin Trigger */
    /* REVIEW  RVW_DEFECT on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="REVIEW"
    CHILD_OWNER="", CHILD_TABLE="RVW_DEFECT"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="REVIEWDEFECTTOREVIEW", FK_COLUMNS="REVIEW_ID" */
    IF EXISTS (
      SELECT * FROM deleted,RVW_DEFECT
      WHERE
        /*  %JoinFKPK(RVW_DEFECT,deleted," = "," AND") */
        RVW_DEFECT.REVIEW_ID = deleted.REVIEW_ID
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete REVIEW because RVW_DEFECT exists.'
      GOTO error
    END


    /* ERwin Builtin Trigger */
    RETURN
error:
    raiserror (@errmsg,16,-1)
    rollback transaction
END

go


CREATE TRIGGER tU_REVIEW ON REVIEW FOR UPDATE AS
/* ERwin Builtin Trigger */
/* UPDATE trigger on REVIEW */
BEGIN
  DECLARE  @numrows int,
           @nullcnt int,
           @validcnt int,
           @insREVIEW_ID varchar(14),
           @errno   int,
           @errmsg  varchar(255)

  SELECT @numrows = @@rowcount
  /* ERwin Builtin Trigger */
  /* REVIEW  RVW_LVL_DEFECT_AUD on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="00035631", PARENT_OWNER="", PARENT_TABLE="REVIEW"
    CHILD_OWNER="", CHILD_TABLE="RVW_LVL_DEFECT_AUD"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="RVWLVLDEFAUDTOREVIEW", FK_COLUMNS="REVIEW_ID" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(REVIEW_ID)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,RVW_LVL_DEFECT_AUD
      WHERE
        /*  %JoinFKPK(RVW_LVL_DEFECT_AUD,deleted," = "," AND") */
        RVW_LVL_DEFECT_AUD.REVIEW_ID = deleted.REVIEW_ID
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update REVIEW because RVW_LVL_DEFECT_AUD exists.'
      GOTO error
    END
  END

  /* ERwin Builtin Trigger */
  /* REVIEW  RVW_LVL_DEFECT on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="REVIEW"
    CHILD_OWNER="", CHILD_TABLE="RVW_LVL_DEFECT"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="REVIEWLEVELDEFECTTOREVIEW", FK_COLUMNS="REVIEW_ID" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(REVIEW_ID)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,RVW_LVL_DEFECT
      WHERE
        /*  %JoinFKPK(RVW_LVL_DEFECT,deleted," = "," AND") */
        RVW_LVL_DEFECT.REVIEW_ID = deleted.REVIEW_ID
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update REVIEW because RVW_LVL_DEFECT exists.'
      GOTO error
    END
  END

  /* ERwin Builtin Trigger */
  /* REVIEW  RVW_DEFECT on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="REVIEW"
    CHILD_OWNER="", CHILD_TABLE="RVW_DEFECT"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="REVIEWDEFECTTOREVIEW", FK_COLUMNS="REVIEW_ID" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(REVIEW_ID)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,RVW_DEFECT
      WHERE
        /*  %JoinFKPK(RVW_DEFECT,deleted," = "," AND") */
        RVW_DEFECT.REVIEW_ID = deleted.REVIEW_ID
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update REVIEW because RVW_DEFECT exists.'
      GOTO error
    END
  END


  /* ERwin Builtin Trigger */
  RETURN
error:
    raiserror (@errmsg,16,-1)
    rollback transaction
END

go




CREATE TRIGGER tD_REVIEW_LEVEL ON REVIEW_LEVEL FOR DELETE AS
/* ERwin Builtin Trigger */
/* DELETE trigger on REVIEW_LEVEL */
BEGIN
  DECLARE  @errno   int,
           @errmsg  varchar(255)
    /* ERwin Builtin Trigger */
    /* REVIEW_LEVEL  RVW_LVL_RFRRL_AUD on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="0008998a", PARENT_OWNER="", PARENT_TABLE="REVIEW_LEVEL"
    CHILD_OWNER="", CHILD_TABLE="RVW_LVL_RFRRL_AUD"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="RVWLVLREFERRALTORVWLEVEL", FK_COLUMNS="REVIEW_LVL_ID" */
    IF EXISTS (
      SELECT * FROM deleted,RVW_LVL_RFRRL_AUD
      WHERE
        /*  %JoinFKPK(RVW_LVL_RFRRL_AUD,deleted," = "," AND") */
        RVW_LVL_RFRRL_AUD.REVIEW_LVL_ID = deleted.REVIEW_LVL_ID
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete REVIEW_LEVEL because RVW_LVL_RFRRL_AUD exists.'
      GOTO error
    END

    /* ERwin Builtin Trigger */
    /* REVIEW_LEVEL  RVW_LVL_INDEM on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="REVIEW_LEVEL"
    CHILD_OWNER="", CHILD_TABLE="RVW_LVL_INDEM"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="RVWLVLINDEMTORVWLEVEL", FK_COLUMNS="REVIEW_LVL_ID" */
    IF EXISTS (
      SELECT * FROM deleted,RVW_LVL_INDEM
      WHERE
        /*  %JoinFKPK(RVW_LVL_INDEM,deleted," = "," AND") */
        RVW_LVL_INDEM.REVIEW_LVL_ID = deleted.REVIEW_LVL_ID
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete REVIEW_LEVEL because RVW_LVL_INDEM exists.'
      GOTO error
    END

    /* ERwin Builtin Trigger */
    /* REVIEW_LEVEL  RVW_LVL_INDEM_AUD on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="REVIEW_LEVEL"
    CHILD_OWNER="", CHILD_TABLE="RVW_LVL_INDEM_AUD"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="RVWLVLINDEMAUDITTORVWLEVEL", FK_COLUMNS="REVIEW_LVL_ID" */
    IF EXISTS (
      SELECT * FROM deleted,RVW_LVL_INDEM_AUD
      WHERE
        /*  %JoinFKPK(RVW_LVL_INDEM_AUD,deleted," = "," AND") */
        RVW_LVL_INDEM_AUD.REVIEW_LVL_ID = deleted.REVIEW_LVL_ID
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete REVIEW_LEVEL because RVW_LVL_INDEM_AUD exists.'
      GOTO error
    END

    /* ERwin Builtin Trigger */
    /* REVIEW_LEVEL  RVW_LVL_FINDING on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="REVIEW_LEVEL"
    CHILD_OWNER="", CHILD_TABLE="RVW_LVL_FINDING"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="RVWLVLFINDINGTORVWLVL", FK_COLUMNS="REVIEW_LVL_ID" */
    IF EXISTS (
      SELECT * FROM deleted,RVW_LVL_FINDING
      WHERE
        /*  %JoinFKPK(RVW_LVL_FINDING,deleted," = "," AND") */
        RVW_LVL_FINDING.REVIEW_LVL_ID = deleted.REVIEW_LVL_ID
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete REVIEW_LEVEL because RVW_LVL_FINDING exists.'
      GOTO error
    END

    /* ERwin Builtin Trigger */
    /* REVIEW_LEVEL  RVW_LVL_FINDING_AUD on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="REVIEW_LEVEL"
    CHILD_OWNER="", CHILD_TABLE="RVW_LVL_FINDING_AUD"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="RVWLVLFINDAUDITTORVWLEVEL", FK_COLUMNS="REVIEW_LVL_ID" */
    IF EXISTS (
      SELECT * FROM deleted,RVW_LVL_FINDING_AUD
      WHERE
        /*  %JoinFKPK(RVW_LVL_FINDING_AUD,deleted," = "," AND") */
        RVW_LVL_FINDING_AUD.REVIEW_LVL_ID = deleted.REVIEW_LVL_ID
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete REVIEW_LEVEL because RVW_LVL_FINDING_AUD exists.'
      GOTO error
    END

    /* ERwin Builtin Trigger */
    /* REVIEW_LEVEL  RVW_LVL_DEFECT_AUD on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="REVIEW_LEVEL"
    CHILD_OWNER="", CHILD_TABLE="RVW_LVL_DEFECT_AUD"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="RVWLVLDEFAUDTOREVIEWLEVEL", FK_COLUMNS="REVIEW_LVL_ID" */
    IF EXISTS (
      SELECT * FROM deleted,RVW_LVL_DEFECT_AUD
      WHERE
        /*  %JoinFKPK(RVW_LVL_DEFECT_AUD,deleted," = "," AND") */
        RVW_LVL_DEFECT_AUD.REVIEW_LVL_ID = deleted.REVIEW_LVL_ID
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete REVIEW_LEVEL because RVW_LVL_DEFECT_AUD exists.'
      GOTO error
    END

    /* ERwin Builtin Trigger */
    /* REVIEW_LEVEL  RVW_LVL_AUDIT on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="REVIEW_LEVEL"
    CHILD_OWNER="", CHILD_TABLE="RVW_LVL_AUDIT"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="REVIEWLEVELTOAUDIT", FK_COLUMNS="REVIEW_LVL_ID" */
    IF EXISTS (
      SELECT * FROM deleted,RVW_LVL_AUDIT
      WHERE
        /*  %JoinFKPK(RVW_LVL_AUDIT,deleted," = "," AND") */
        RVW_LVL_AUDIT.REVIEW_LVL_ID = deleted.REVIEW_LVL_ID
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete REVIEW_LEVEL because RVW_LVL_AUDIT exists.'
      GOTO error
    END

    /* ERwin Builtin Trigger */
    /* REVIEW_LEVEL  RVW_LVL_DEFECT on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="REVIEW_LEVEL"
    CHILD_OWNER="", CHILD_TABLE="RVW_LVL_DEFECT"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="REVIEWLEVELDEFECTTORVWLVL", FK_COLUMNS="REVIEW_LVL_ID" */
    IF EXISTS (
      SELECT * FROM deleted,RVW_LVL_DEFECT
      WHERE
        /*  %JoinFKPK(RVW_LVL_DEFECT,deleted," = "," AND") */
        RVW_LVL_DEFECT.REVIEW_LVL_ID = deleted.REVIEW_LVL_ID
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete REVIEW_LEVEL because RVW_LVL_DEFECT exists.'
      GOTO error
    END


    /* ERwin Builtin Trigger */
    RETURN
error:
    raiserror (@errmsg,16,-1)
    rollback transaction
END

go


CREATE TRIGGER tU_REVIEW_LEVEL ON REVIEW_LEVEL FOR UPDATE AS
/* ERwin Builtin Trigger */
/* UPDATE trigger on REVIEW_LEVEL */
BEGIN
  DECLARE  @numrows int,
           @nullcnt int,
           @validcnt int,
           @insREVIEW_LVL_ID CHAR(36),
           @errno   int,
           @errmsg  varchar(255)

  SELECT @numrows = @@rowcount
  /* ERwin Builtin Trigger */
  /* REVIEW_LEVEL  RVW_LVL_RFRRL_AUD on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="00096b23", PARENT_OWNER="", PARENT_TABLE="REVIEW_LEVEL"
    CHILD_OWNER="", CHILD_TABLE="RVW_LVL_RFRRL_AUD"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="RVWLVLREFERRALTORVWLEVEL", FK_COLUMNS="REVIEW_LVL_ID" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(REVIEW_LVL_ID)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,RVW_LVL_RFRRL_AUD
      WHERE
        /*  %JoinFKPK(RVW_LVL_RFRRL_AUD,deleted," = "," AND") */
        RVW_LVL_RFRRL_AUD.REVIEW_LVL_ID = deleted.REVIEW_LVL_ID
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update REVIEW_LEVEL because RVW_LVL_RFRRL_AUD exists.'
      GOTO error
    END
  END

  /* ERwin Builtin Trigger */
  /* REVIEW_LEVEL  RVW_LVL_INDEM on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="REVIEW_LEVEL"
    CHILD_OWNER="", CHILD_TABLE="RVW_LVL_INDEM"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="RVWLVLINDEMTORVWLEVEL", FK_COLUMNS="REVIEW_LVL_ID" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(REVIEW_LVL_ID)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,RVW_LVL_INDEM
      WHERE
        /*  %JoinFKPK(RVW_LVL_INDEM,deleted," = "," AND") */
        RVW_LVL_INDEM.REVIEW_LVL_ID = deleted.REVIEW_LVL_ID
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update REVIEW_LEVEL because RVW_LVL_INDEM exists.'
      GOTO error
    END
  END

  /* ERwin Builtin Trigger */
  /* REVIEW_LEVEL  RVW_LVL_INDEM_AUD on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="REVIEW_LEVEL"
    CHILD_OWNER="", CHILD_TABLE="RVW_LVL_INDEM_AUD"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="RVWLVLINDEMAUDITTORVWLEVEL", FK_COLUMNS="REVIEW_LVL_ID" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(REVIEW_LVL_ID)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,RVW_LVL_INDEM_AUD
      WHERE
        /*  %JoinFKPK(RVW_LVL_INDEM_AUD,deleted," = "," AND") */
        RVW_LVL_INDEM_AUD.REVIEW_LVL_ID = deleted.REVIEW_LVL_ID
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update REVIEW_LEVEL because RVW_LVL_INDEM_AUD exists.'
      GOTO error
    END
  END

  /* ERwin Builtin Trigger */
  /* REVIEW_LEVEL  RVW_LVL_FINDING on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="REVIEW_LEVEL"
    CHILD_OWNER="", CHILD_TABLE="RVW_LVL_FINDING"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="RVWLVLFINDINGTORVWLVL", FK_COLUMNS="REVIEW_LVL_ID" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(REVIEW_LVL_ID)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,RVW_LVL_FINDING
      WHERE
        /*  %JoinFKPK(RVW_LVL_FINDING,deleted," = "," AND") */
        RVW_LVL_FINDING.REVIEW_LVL_ID = deleted.REVIEW_LVL_ID
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update REVIEW_LEVEL because RVW_LVL_FINDING exists.'
      GOTO error
    END
  END

  /* ERwin Builtin Trigger */
  /* REVIEW_LEVEL  RVW_LVL_FINDING_AUD on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="REVIEW_LEVEL"
    CHILD_OWNER="", CHILD_TABLE="RVW_LVL_FINDING_AUD"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="RVWLVLFINDAUDITTORVWLEVEL", FK_COLUMNS="REVIEW_LVL_ID" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(REVIEW_LVL_ID)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,RVW_LVL_FINDING_AUD
      WHERE
        /*  %JoinFKPK(RVW_LVL_FINDING_AUD,deleted," = "," AND") */
        RVW_LVL_FINDING_AUD.REVIEW_LVL_ID = deleted.REVIEW_LVL_ID
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update REVIEW_LEVEL because RVW_LVL_FINDING_AUD exists.'
      GOTO error
    END
  END

  /* ERwin Builtin Trigger */
  /* REVIEW_LEVEL  RVW_LVL_DEFECT_AUD on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="REVIEW_LEVEL"
    CHILD_OWNER="", CHILD_TABLE="RVW_LVL_DEFECT_AUD"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="RVWLVLDEFAUDTOREVIEWLEVEL", FK_COLUMNS="REVIEW_LVL_ID" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(REVIEW_LVL_ID)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,RVW_LVL_DEFECT_AUD
      WHERE
        /*  %JoinFKPK(RVW_LVL_DEFECT_AUD,deleted," = "," AND") */
        RVW_LVL_DEFECT_AUD.REVIEW_LVL_ID = deleted.REVIEW_LVL_ID
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update REVIEW_LEVEL because RVW_LVL_DEFECT_AUD exists.'
      GOTO error
    END
  END

  /* ERwin Builtin Trigger */
  /* REVIEW_LEVEL  RVW_LVL_AUDIT on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="REVIEW_LEVEL"
    CHILD_OWNER="", CHILD_TABLE="RVW_LVL_AUDIT"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="REVIEWLEVELTOAUDIT", FK_COLUMNS="REVIEW_LVL_ID" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(REVIEW_LVL_ID)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,RVW_LVL_AUDIT
      WHERE
        /*  %JoinFKPK(RVW_LVL_AUDIT,deleted," = "," AND") */
        RVW_LVL_AUDIT.REVIEW_LVL_ID = deleted.REVIEW_LVL_ID
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update REVIEW_LEVEL because RVW_LVL_AUDIT exists.'
      GOTO error
    END
  END

  /* ERwin Builtin Trigger */
  /* REVIEW_LEVEL  RVW_LVL_DEFECT on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="REVIEW_LEVEL"
    CHILD_OWNER="", CHILD_TABLE="RVW_LVL_DEFECT"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="REVIEWLEVELDEFECTTORVWLVL", FK_COLUMNS="REVIEW_LVL_ID" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(REVIEW_LVL_ID)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,RVW_LVL_DEFECT
      WHERE
        /*  %JoinFKPK(RVW_LVL_DEFECT,deleted," = "," AND") */
        RVW_LVL_DEFECT.REVIEW_LVL_ID = deleted.REVIEW_LVL_ID
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update REVIEW_LEVEL because RVW_LVL_DEFECT exists.'
      GOTO error
    END
  END


  /* ERwin Builtin Trigger */
  RETURN
error:
    raiserror (@errmsg,16,-1)
    rollback transaction
END

go




CREATE TRIGGER tD_RVW_BATCH ON RVW_BATCH FOR DELETE AS
/* ERwin Builtin Trigger */
/* DELETE trigger on RVW_BATCH */
BEGIN
  DECLARE  @errno   int,
           @errmsg  varchar(255)
    /* ERwin Builtin Trigger */
    /* RVW_BATCH  REVIEW on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="0000eacd", PARENT_OWNER="", PARENT_TABLE="RVW_BATCH"
    CHILD_OWNER="", CHILD_TABLE="REVIEW"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="REVIEWBATCH", FK_COLUMNS="BATCH_ID" */
    IF EXISTS (
      SELECT * FROM deleted,REVIEW
      WHERE
        /*  %JoinFKPK(REVIEW,deleted," = "," AND") */
        REVIEW.BATCH_ID = deleted.BATCH_ID
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete RVW_BATCH because REVIEW exists.'
      GOTO error
    END


    /* ERwin Builtin Trigger */
    RETURN
error:
    raiserror (@errmsg,16,-1)
    rollback transaction
END

go


CREATE TRIGGER tU_RVW_BATCH ON RVW_BATCH FOR UPDATE AS
/* ERwin Builtin Trigger */
/* UPDATE trigger on RVW_BATCH */
BEGIN
  DECLARE  @numrows int,
           @nullcnt int,
           @validcnt int,
           @insBATCH_ID varchar(16),
           @errno   int,
           @errmsg  varchar(255)

  SELECT @numrows = @@rowcount
  /* ERwin Builtin Trigger */
  /* RVW_BATCH  REVIEW on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="000100c5", PARENT_OWNER="", PARENT_TABLE="RVW_BATCH"
    CHILD_OWNER="", CHILD_TABLE="REVIEW"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="REVIEWBATCH", FK_COLUMNS="BATCH_ID" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(BATCH_ID)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,REVIEW
      WHERE
        /*  %JoinFKPK(REVIEW,deleted," = "," AND") */
        REVIEW.BATCH_ID = deleted.BATCH_ID
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update RVW_BATCH because REVIEW exists.'
      GOTO error
    END
  END


  /* ERwin Builtin Trigger */
  RETURN
error:
    raiserror (@errmsg,16,-1)
    rollback transaction
END

go




CREATE TRIGGER tD_REVIEW_LEVEL_ADMIN ON REVIEW_LEVEL_ADMIN FOR DELETE AS
/* ERwin Builtin Trigger */
/* DELETE trigger on REVIEW_LEVEL_ADMIN */
BEGIN
  DECLARE  @errno   int,
           @errmsg  varchar(255)
    /* ERwin Builtin Trigger */
    /* REVIEW_LEVEL_ADMIN  DELIV_PARMS_ITERATION on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="0002505b", PARENT_OWNER="", PARENT_TABLE="REVIEW_LEVEL_ADMIN"
    CHILD_OWNER="", CHILD_TABLE="DELIV_PARMS_ITERATION"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="DELIVPARMSREVLEVEL", FK_COLUMNS="REVIEW_LEVEL_CD" */
    IF EXISTS (
      SELECT * FROM deleted,DELIV_PARMS_ITERATION
      WHERE
        /*  %JoinFKPK(DELIV_PARMS_ITERATION,deleted," = "," AND") */
        DELIV_PARMS_ITERATION.REVIEW_LEVEL_CD = deleted.REVIEW_LEVEL_CD
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete REVIEW_LEVEL_ADMIN because DELIV_PARMS_ITERATION exists.'
      GOTO error
    END

    /* ERwin Builtin Trigger */
    /* REVIEW_LEVEL_ADMIN  DELIV_PARMS_REQUEST on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="REVIEW_LEVEL_ADMIN"
    CHILD_OWNER="", CHILD_TABLE="DELIV_PARMS_REQUEST"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="DELIVPARMSREQREVLEVEL", FK_COLUMNS="REVIEW_LEVEL_CD" */
    IF EXISTS (
      SELECT * FROM deleted,DELIV_PARMS_REQUEST
      WHERE
        /*  %JoinFKPK(DELIV_PARMS_REQUEST,deleted," = "," AND") */
        DELIV_PARMS_REQUEST.REVIEW_LEVEL_CD = deleted.REVIEW_LEVEL_CD
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete REVIEW_LEVEL_ADMIN because DELIV_PARMS_REQUEST exists.'
      GOTO error
    END


    /* ERwin Builtin Trigger */
    RETURN
error:
    raiserror (@errmsg,16,-1)
    rollback transaction
END

go


CREATE TRIGGER tU_REVIEW_LEVEL_ADMIN ON REVIEW_LEVEL_ADMIN FOR UPDATE AS
/* ERwin Builtin Trigger */
/* UPDATE trigger on REVIEW_LEVEL_ADMIN */
BEGIN
  DECLARE  @numrows int,
           @nullcnt int,
           @validcnt int,
           @insREVIEW_LEVEL_CD varchar(16),
           @errno   int,
           @errmsg  varchar(255)

  SELECT @numrows = @@rowcount
  /* ERwin Builtin Trigger */
  /* REVIEW_LEVEL_ADMIN  DELIV_PARMS_ITERATION on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="00029d26", PARENT_OWNER="", PARENT_TABLE="REVIEW_LEVEL_ADMIN"
    CHILD_OWNER="", CHILD_TABLE="DELIV_PARMS_ITERATION"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="DELIVPARMSREVLEVEL", FK_COLUMNS="REVIEW_LEVEL_CD" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(REVIEW_LEVEL_CD)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,DELIV_PARMS_ITERATION
      WHERE
        /*  %JoinFKPK(DELIV_PARMS_ITERATION,deleted," = "," AND") */
        DELIV_PARMS_ITERATION.REVIEW_LEVEL_CD = deleted.REVIEW_LEVEL_CD
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update REVIEW_LEVEL_ADMIN because DELIV_PARMS_ITERATION exists.'
      GOTO error
    END
  END

  /* ERwin Builtin Trigger */
  /* REVIEW_LEVEL_ADMIN  DELIV_PARMS_REQUEST on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="REVIEW_LEVEL_ADMIN"
    CHILD_OWNER="", CHILD_TABLE="DELIV_PARMS_REQUEST"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="DELIVPARMSREQREVLEVEL", FK_COLUMNS="REVIEW_LEVEL_CD" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(REVIEW_LEVEL_CD)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,DELIV_PARMS_REQUEST
      WHERE
        /*  %JoinFKPK(DELIV_PARMS_REQUEST,deleted," = "," AND") */
        DELIV_PARMS_REQUEST.REVIEW_LEVEL_CD = deleted.REVIEW_LEVEL_CD
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update REVIEW_LEVEL_ADMIN because DELIV_PARMS_REQUEST exists.'
      GOTO error
    END
  END


  /* ERwin Builtin Trigger */
  RETURN
error:
    raiserror (@errmsg,16,-1)
    rollback transaction
END

go




CREATE TRIGGER tD_SELECTION_PARAMETERS ON SELECTION_PARAMETERS FOR DELETE AS
/* ERwin Builtin Trigger */
/* DELETE trigger on SELECTION_PARAMETERS */
BEGIN
  DECLARE  @errno   int,
           @errmsg  varchar(255)
    /* ERwin Builtin Trigger */
    /* SELECTION_PARAMETERS  QATREE_QSTN_RULES on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="0004bc0f", PARENT_OWNER="", PARENT_TABLE="SELECTION_PARAMETERS"
    CHILD_OWNER="", CHILD_TABLE="QATREE_QSTN_RULES"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="QUESTIONRULESTOSELREASON", FK_COLUMNS="SLCTN_REASON_ID" */
    IF EXISTS (
      SELECT * FROM deleted,QATREE_QSTN_RULES
      WHERE
        /*  %JoinFKPK(QATREE_QSTN_RULES,deleted," = "," AND") */
        QATREE_QSTN_RULES.SLCTN_REASON_ID = deleted.SLCTN_REASON_ID
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete SELECTION_PARAMETERS because QATREE_QSTN_RULES exists.'
      GOTO error
    END

    /* ERwin Builtin Trigger */
    /* SELECTION_PARAMETERS  LOAN_SELECTION_PENDING on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="SELECTION_PARAMETERS"
    CHILD_OWNER="", CHILD_TABLE="LOAN_SELECTION_PENDING"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="PENDSELECTIONTOREASON", FK_COLUMNS="SLCTN_REASON_ID" */
    IF EXISTS (
      SELECT * FROM deleted,LOAN_SELECTION_PENDING
      WHERE
        /*  %JoinFKPK(LOAN_SELECTION_PENDING,deleted," = "," AND") */
        LOAN_SELECTION_PENDING.SLCTN_REASON_ID = deleted.SLCTN_REASON_ID
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete SELECTION_PARAMETERS because LOAN_SELECTION_PENDING exists.'
      GOTO error
    END

    /* ERwin Builtin Trigger */
    /* SELECTION_PARAMETERS  DELIV_PARMS_ITERATION on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="SELECTION_PARAMETERS"
    CHILD_OWNER="", CHILD_TABLE="DELIV_PARMS_ITERATION"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="DELIVPARMSSELPARMS", FK_COLUMNS="SLCTN_REASON_ID" */
    IF EXISTS (
      SELECT * FROM deleted,DELIV_PARMS_ITERATION
      WHERE
        /*  %JoinFKPK(DELIV_PARMS_ITERATION,deleted," = "," AND") */
        DELIV_PARMS_ITERATION.SLCTN_REASON_ID = deleted.SLCTN_REASON_ID
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete SELECTION_PARAMETERS because DELIV_PARMS_ITERATION exists.'
      GOTO error
    END

    /* ERwin Builtin Trigger */
    /* SELECTION_PARAMETERS  DELIV_PARMS_REQUEST on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="SELECTION_PARAMETERS"
    CHILD_OWNER="", CHILD_TABLE="DELIV_PARMS_REQUEST"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="DELIVPARMSREQSELPARMS", FK_COLUMNS="SLCTN_REASON_ID" */
    IF EXISTS (
      SELECT * FROM deleted,DELIV_PARMS_REQUEST
      WHERE
        /*  %JoinFKPK(DELIV_PARMS_REQUEST,deleted," = "," AND") */
        DELIV_PARMS_REQUEST.SLCTN_REASON_ID = deleted.SLCTN_REASON_ID
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete SELECTION_PARAMETERS because DELIV_PARMS_REQUEST exists.'
      GOTO error
    END


    /* ERwin Builtin Trigger */
    RETURN
error:
    raiserror (@errmsg,16,-1)
    rollback transaction
END

go


CREATE TRIGGER tU_SELECTION_PARAMETERS ON SELECTION_PARAMETERS FOR UPDATE AS
/* ERwin Builtin Trigger */
/* UPDATE trigger on SELECTION_PARAMETERS */
BEGIN
  DECLARE  @numrows int,
           @nullcnt int,
           @validcnt int,
           @insSLCTN_REASON_ID varchar(16),
           @errno   int,
           @errmsg  varchar(255)

  SELECT @numrows = @@rowcount
  /* ERwin Builtin Trigger */
  /* SELECTION_PARAMETERS  QATREE_QSTN_RULES on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="00052e7a", PARENT_OWNER="", PARENT_TABLE="SELECTION_PARAMETERS"
    CHILD_OWNER="", CHILD_TABLE="QATREE_QSTN_RULES"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="QUESTIONRULESTOSELREASON", FK_COLUMNS="SLCTN_REASON_ID" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(SLCTN_REASON_ID)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,QATREE_QSTN_RULES
      WHERE
        /*  %JoinFKPK(QATREE_QSTN_RULES,deleted," = "," AND") */
        QATREE_QSTN_RULES.SLCTN_REASON_ID = deleted.SLCTN_REASON_ID
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update SELECTION_PARAMETERS because QATREE_QSTN_RULES exists.'
      GOTO error
    END
  END

  /* ERwin Builtin Trigger */
  /* SELECTION_PARAMETERS  LOAN_SELECTION_PENDING on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="SELECTION_PARAMETERS"
    CHILD_OWNER="", CHILD_TABLE="LOAN_SELECTION_PENDING"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="PENDSELECTIONTOREASON", FK_COLUMNS="SLCTN_REASON_ID" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(SLCTN_REASON_ID)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,LOAN_SELECTION_PENDING
      WHERE
        /*  %JoinFKPK(LOAN_SELECTION_PENDING,deleted," = "," AND") */
        LOAN_SELECTION_PENDING.SLCTN_REASON_ID = deleted.SLCTN_REASON_ID
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update SELECTION_PARAMETERS because LOAN_SELECTION_PENDING exists.'
      GOTO error
    END
  END

  /* ERwin Builtin Trigger */
  /* SELECTION_PARAMETERS  DELIV_PARMS_ITERATION on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="SELECTION_PARAMETERS"
    CHILD_OWNER="", CHILD_TABLE="DELIV_PARMS_ITERATION"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="DELIVPARMSSELPARMS", FK_COLUMNS="SLCTN_REASON_ID" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(SLCTN_REASON_ID)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,DELIV_PARMS_ITERATION
      WHERE
        /*  %JoinFKPK(DELIV_PARMS_ITERATION,deleted," = "," AND") */
        DELIV_PARMS_ITERATION.SLCTN_REASON_ID = deleted.SLCTN_REASON_ID
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update SELECTION_PARAMETERS because DELIV_PARMS_ITERATION exists.'
      GOTO error
    END
  END

  /* ERwin Builtin Trigger */
  /* SELECTION_PARAMETERS  DELIV_PARMS_REQUEST on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="SELECTION_PARAMETERS"
    CHILD_OWNER="", CHILD_TABLE="DELIV_PARMS_REQUEST"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="DELIVPARMSREQSELPARMS", FK_COLUMNS="SLCTN_REASON_ID" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(SLCTN_REASON_ID)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,DELIV_PARMS_REQUEST
      WHERE
        /*  %JoinFKPK(DELIV_PARMS_REQUEST,deleted," = "," AND") */
        DELIV_PARMS_REQUEST.SLCTN_REASON_ID = deleted.SLCTN_REASON_ID
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update SELECTION_PARAMETERS because DELIV_PARMS_REQUEST exists.'
      GOTO error
    END
  END


  /* ERwin Builtin Trigger */
  RETURN
error:
    raiserror (@errmsg,16,-1)
    rollback transaction
END

go




CREATE TRIGGER tD_RVW_LVL_DEFECT ON RVW_LVL_DEFECT FOR DELETE AS
/* ERwin Builtin Trigger */
/* DELETE trigger on RVW_LVL_DEFECT */
BEGIN
  DECLARE  @errno   int,
           @errmsg  varchar(255)
    /* ERwin Builtin Trigger */
    /* RVW_LVL_DEFECT  RVW_LVL_DEFECT_AUD on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="0001417c", PARENT_OWNER="", PARENT_TABLE="RVW_LVL_DEFECT"
    CHILD_OWNER="", CHILD_TABLE="RVW_LVL_DEFECT_AUD"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="RVWLVLDEFAUDTORVWLVLDEF", FK_COLUMNS="REVIEW_LVL_ID""DEFECT_CD" */
    IF EXISTS (
      SELECT * FROM deleted,RVW_LVL_DEFECT_AUD
      WHERE
        /*  %JoinFKPK(RVW_LVL_DEFECT_AUD,deleted," = "," AND") */
        RVW_LVL_DEFECT_AUD.REVIEW_LVL_ID = deleted.REVIEW_LVL_ID AND
        RVW_LVL_DEFECT_AUD.DEFECT_CD = deleted.DEFECT_CD
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete RVW_LVL_DEFECT because RVW_LVL_DEFECT_AUD exists.'
      GOTO error
    END


    /* ERwin Builtin Trigger */
    RETURN
error:
    raiserror (@errmsg,16,-1)
    rollback transaction
END

go


CREATE TRIGGER tU_RVW_LVL_DEFECT ON RVW_LVL_DEFECT FOR UPDATE AS
/* ERwin Builtin Trigger */
/* UPDATE trigger on RVW_LVL_DEFECT */
BEGIN
  DECLARE  @numrows int,
           @nullcnt int,
           @validcnt int,
           @insREVIEW_LVL_ID CHAR(36), 
           @insDEFECT_CD varchar(16),
           @errno   int,
           @errmsg  varchar(255)

  SELECT @numrows = @@rowcount
  /* ERwin Builtin Trigger */
  /* RVW_LVL_DEFECT  RVW_LVL_DEFECT_AUD on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="00016b9f", PARENT_OWNER="", PARENT_TABLE="RVW_LVL_DEFECT"
    CHILD_OWNER="", CHILD_TABLE="RVW_LVL_DEFECT_AUD"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="RVWLVLDEFAUDTORVWLVLDEF", FK_COLUMNS="REVIEW_LVL_ID""DEFECT_CD" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(REVIEW_LVL_ID) OR
    UPDATE(DEFECT_CD)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,RVW_LVL_DEFECT_AUD
      WHERE
        /*  %JoinFKPK(RVW_LVL_DEFECT_AUD,deleted," = "," AND") */
        RVW_LVL_DEFECT_AUD.REVIEW_LVL_ID = deleted.REVIEW_LVL_ID AND
        RVW_LVL_DEFECT_AUD.DEFECT_CD = deleted.DEFECT_CD
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update RVW_LVL_DEFECT because RVW_LVL_DEFECT_AUD exists.'
      GOTO error
    END
  END


  /* ERwin Builtin Trigger */
  RETURN
error:
    raiserror (@errmsg,16,-1)
    rollback transaction
END

go




CREATE TRIGGER tD_RVW_LVL_INDEM ON RVW_LVL_INDEM FOR DELETE AS
/* ERwin Builtin Trigger */
/* DELETE trigger on RVW_LVL_INDEM */
BEGIN
  DECLARE  @errno   int,
           @errmsg  varchar(255)
    /* ERwin Builtin Trigger */
    /* RVW_LVL_INDEM  RVW_LVL_INDEM_AUD on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00013d46", PARENT_OWNER="", PARENT_TABLE="RVW_LVL_INDEM"
    CHILD_OWNER="", CHILD_TABLE="RVW_LVL_INDEM_AUD"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="RVWLVLINDEMAUDTORVWLVLIND", FK_COLUMNS="REVIEW_LVL_ID""INDEM_TYPE" */
    IF EXISTS (
      SELECT * FROM deleted,RVW_LVL_INDEM_AUD
      WHERE
        /*  %JoinFKPK(RVW_LVL_INDEM_AUD,deleted," = "," AND") */
        RVW_LVL_INDEM_AUD.REVIEW_LVL_ID = deleted.REVIEW_LVL_ID AND
        RVW_LVL_INDEM_AUD.INDEM_TYPE = deleted.INDEM_TYPE
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete RVW_LVL_INDEM because RVW_LVL_INDEM_AUD exists.'
      GOTO error
    END


    /* ERwin Builtin Trigger */
    RETURN
error:
    raiserror (@errmsg,16,-1)
    rollback transaction
END

go


CREATE TRIGGER tU_RVW_LVL_INDEM ON RVW_LVL_INDEM FOR UPDATE AS
/* ERwin Builtin Trigger */
/* UPDATE trigger on RVW_LVL_INDEM */
BEGIN
  DECLARE  @numrows int,
           @nullcnt int,
           @validcnt int,
           @insREVIEW_LVL_ID CHAR(36), 
           @insINDEM_TYPE varchar(16),
           @errno   int,
           @errmsg  varchar(255)

  SELECT @numrows = @@rowcount
  /* ERwin Builtin Trigger */
  /* RVW_LVL_INDEM  RVW_LVL_INDEM_AUD on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="00016bda", PARENT_OWNER="", PARENT_TABLE="RVW_LVL_INDEM"
    CHILD_OWNER="", CHILD_TABLE="RVW_LVL_INDEM_AUD"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="RVWLVLINDEMAUDTORVWLVLIND", FK_COLUMNS="REVIEW_LVL_ID""INDEM_TYPE" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(REVIEW_LVL_ID) OR
    UPDATE(INDEM_TYPE)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,RVW_LVL_INDEM_AUD
      WHERE
        /*  %JoinFKPK(RVW_LVL_INDEM_AUD,deleted," = "," AND") */
        RVW_LVL_INDEM_AUD.REVIEW_LVL_ID = deleted.REVIEW_LVL_ID AND
        RVW_LVL_INDEM_AUD.INDEM_TYPE = deleted.INDEM_TYPE
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update RVW_LVL_INDEM because RVW_LVL_INDEM_AUD exists.'
      GOTO error
    END
  END


  /* ERwin Builtin Trigger */
  RETURN
error:
    raiserror (@errmsg,16,-1)
    rollback transaction
END

go




CREATE TRIGGER tD_SCORING_MODEL ON SCORING_MODEL FOR DELETE AS
/* ERwin Builtin Trigger */
/* DELETE trigger on SCORING_MODEL */
BEGIN
  DECLARE  @errno   int,
           @errmsg  varchar(255)
    /* ERwin Builtin Trigger */
    /* SCORING_MODEL  MODEL_SCORE on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00033a83", PARENT_OWNER="", PARENT_TABLE="SCORING_MODEL"
    CHILD_OWNER="", CHILD_TABLE="MODEL_SCORE"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="SCORETOMODEL", FK_COLUMNS="MODEL_ID" */
    IF EXISTS (
      SELECT * FROM deleted,MODEL_SCORE
      WHERE
        /*  %JoinFKPK(MODEL_SCORE,deleted," = "," AND") */
        MODEL_SCORE.MODEL_ID = deleted.MODEL_ID
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete SCORING_MODEL because MODEL_SCORE exists.'
      GOTO error
    END

    /* ERwin Builtin Trigger */
    /* SCORING_MODEL  SCORING_MODEL_VERSION on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="SCORING_MODEL"
    CHILD_OWNER="", CHILD_TABLE="SCORING_MODEL_VERSION"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="MODELVERSIONTOMODEL", FK_COLUMNS="MODEL_ID" */
    IF EXISTS (
      SELECT * FROM deleted,SCORING_MODEL_VERSION
      WHERE
        /*  %JoinFKPK(SCORING_MODEL_VERSION,deleted," = "," AND") */
        SCORING_MODEL_VERSION.MODEL_ID = deleted.MODEL_ID
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete SCORING_MODEL because SCORING_MODEL_VERSION exists.'
      GOTO error
    END

    /* ERwin Builtin Trigger */
    /* SCORING_MODEL  SCORING_MODEL_FACTORS on parent delete no action */
    /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="SCORING_MODEL"
    CHILD_OWNER="", CHILD_TABLE="SCORING_MODEL_FACTORS"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="MODELFACTORSTOMODEL", FK_COLUMNS="MODEL_ID" */
    IF EXISTS (
      SELECT * FROM deleted,SCORING_MODEL_FACTORS
      WHERE
        /*  %JoinFKPK(SCORING_MODEL_FACTORS,deleted," = "," AND") */
        SCORING_MODEL_FACTORS.MODEL_ID = deleted.MODEL_ID
    )
    BEGIN
      SELECT @errno  = 30001,
             @errmsg = 'Cannot delete SCORING_MODEL because SCORING_MODEL_FACTORS exists.'
      GOTO error
    END


    /* ERwin Builtin Trigger */
    RETURN
error:
    raiserror (@errmsg,16,-1)
    rollback transaction
END

go


CREATE TRIGGER tU_SCORING_MODEL ON SCORING_MODEL FOR UPDATE AS
/* ERwin Builtin Trigger */
/* UPDATE trigger on SCORING_MODEL */
BEGIN
  DECLARE  @numrows int,
           @nullcnt int,
           @validcnt int,
           @insMODEL_ID varchar(30),
           @errno   int,
           @errmsg  varchar(255)

  SELECT @numrows = @@rowcount
  /* ERwin Builtin Trigger */
  /* SCORING_MODEL  MODEL_SCORE on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="0003863a", PARENT_OWNER="", PARENT_TABLE="SCORING_MODEL"
    CHILD_OWNER="", CHILD_TABLE="MODEL_SCORE"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="SCORETOMODEL", FK_COLUMNS="MODEL_ID" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(MODEL_ID)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,MODEL_SCORE
      WHERE
        /*  %JoinFKPK(MODEL_SCORE,deleted," = "," AND") */
        MODEL_SCORE.MODEL_ID = deleted.MODEL_ID
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update SCORING_MODEL because MODEL_SCORE exists.'
      GOTO error
    END
  END

  /* ERwin Builtin Trigger */
  /* SCORING_MODEL  SCORING_MODEL_VERSION on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="SCORING_MODEL"
    CHILD_OWNER="", CHILD_TABLE="SCORING_MODEL_VERSION"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="MODELVERSIONTOMODEL", FK_COLUMNS="MODEL_ID" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(MODEL_ID)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,SCORING_MODEL_VERSION
      WHERE
        /*  %JoinFKPK(SCORING_MODEL_VERSION,deleted," = "," AND") */
        SCORING_MODEL_VERSION.MODEL_ID = deleted.MODEL_ID
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update SCORING_MODEL because SCORING_MODEL_VERSION exists.'
      GOTO error
    END
  END

  /* ERwin Builtin Trigger */
  /* SCORING_MODEL  SCORING_MODEL_FACTORS on parent update no action */
  /* ERWIN_RELATION:CHECKSUM="00000000", PARENT_OWNER="", PARENT_TABLE="SCORING_MODEL"
    CHILD_OWNER="", CHILD_TABLE="SCORING_MODEL_FACTORS"
    P2C_VERB_PHRASE="", C2P_VERB_PHRASE="", 
    FK_CONSTRAINT="MODELFACTORSTOMODEL", FK_COLUMNS="MODEL_ID" */
  IF
    /* %ParentPK(" OR",UPDATE) */
    UPDATE(MODEL_ID)
  BEGIN
    IF EXISTS (
      SELECT * FROM deleted,SCORING_MODEL_FACTORS
      WHERE
        /*  %JoinFKPK(SCORING_MODEL_FACTORS,deleted," = "," AND") */
        SCORING_MODEL_FACTORS.MODEL_ID = deleted.MODEL_ID
    )
    BEGIN
      SELECT @errno  = 30005,
             @errmsg = 'Cannot update SCORING_MODEL because SCORING_MODEL_FACTORS exists.'
      GOTO error
    END
  END


  /* ERwin Builtin Trigger */
  RETURN
error:
    raiserror (@errmsg,16,-1)
    rollback transaction
END

go


