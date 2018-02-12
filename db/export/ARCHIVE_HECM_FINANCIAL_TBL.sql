set echo off
set pagesize 0
set trimspool on
set long 320000
set longc 320000
set lin 32767
set recsep off
set feed off
set trims on
set termout off
spool ARCHIVE_HECM_FINANCIAL_TBL.out
SELECT substr(lpad(case_no,10,'0'), 1,3) || '-' || substr(lpad(case_no,10,'0'), 4,11) 
 || '{~~}' || CF_EQUIVICAL_ASSETS_IND                                                 
 || '{~~}' || CF_EXPECTED_SSI_IND                                                     
 || '{~~}' || CF_HECM_SUFFICIENT_IND                                                  
 || '{~~}' || CF_IMPUTED_INCOME_IND                                                   
 || '{~~}' || CF_NBS_INCOME_IND                                                       
 || '{~~}' || CF_OT_INCOME_IND                                                        
 || '{~~}' || CF_OTHER_INCOME_IND                                                     
 || '{~~}' || CF_PROP_PMT_HIST_IND                                                    
 || '{~~}' || CONDO_FEE_CURR_IND                                                      
 || '{~~}' || CONDO_FEE_DELINQ_IND                                                    
 || '{~~}' || case when create_date < 19000000 then to_date(19800101, 'YYYY-MM-DD') else to_date(create_date, 'YYYY-MM-DD') end                                                      
 || '{~~}' || CREATE_ID                                                               
 || '{~~}' || LE_COMPOUND_RATE                                                        
 || '{~~}' || LE_EXPECTED_RATE                                                        
 || '{~~}' || LE_PROJECTED_AMT                                                        
 || '{~~}' || LE_SETASIDE_AMT                                                         
 || '{~~}' || LE_TALC_MONTHS                                                          
 || '{~~}' || LESA_FUNDING_AMT                                                        
 || '{~~}' || LESA_FUNDING_TYPE                                                       
 || '{~~}' || ME_DEBT_AMT                                                             
 || '{~~}' || ME_OTHER_AMT                                                            
 || '{~~}' || ME_RE_AMT                                                               
 || '{~~}' || ME_TOTAL_AMT                                                            
 || '{~~}' || MI_IMPUTED_AMT                                                          
 || '{~~}' || MI_OTHER_AMT                                                            
 || '{~~}' || MI_TOTAL_AMT                                                            
 || '{~~}' || OTHER_DEBT_CURR_IND                                                     
 || '{~~}' || OTHER_DEBT_LATE_PMT_IND                                                 
 || '{~~}' || PC_CONDO_FEE_AMT                                                        
 || '{~~}' || PC_FLOOD_INS_AMT                                                        
 || '{~~}' || PC_HAZ_INS_AMT                                                          
 || '{~~}' || PC_OTHER_AMT                                                            
 || '{~~}' || PC_RE_TAX_AMT                                                           
 || '{~~}' || PC_SETASIDE_TOT_AMT                                                     
 || '{~~}' || PC_TOTAL_AMT                                                            
 || '{~~}' || RE_DEBT_CURR_IND                                                        
 || '{~~}' || RE_DEBT_LATE_PMT_IND                                                    
 || '{~~}' || RE_TAX_CURR_IND                                                         
 || '{~~}' || RE_TAX_DELINQ_IND                                                       
 || '{~~}' || REVOLVE_DEBT_CURR_IND                                                   
 || '{~~}' || REVOLVE_DEBT_LATE_PMT_IND                                               
 || '{~~}' || RI_FAMILY_SIZE                                                          
 || '{~~}' || RI_SHORTFALL_AMT                                                        
 || '{~~}' || RI_STANDARD_AMT                                                         
 || '{~~}' || RI_TOTAL_AMT                                                            
 || '{~~}' || to_char(sysdate, 'YYYY-MM-DD')   
 || '{~~}' || update_id  
 || '{~~}' || 'N'
 || '!~!' from ARCHIVE_HECM_FINANCIAL_TBL 
 --where case_no in (118318468, 118318763)
	;

spool off
exit
