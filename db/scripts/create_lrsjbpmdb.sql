USE [master]
GO

/****** Object:  Database [LRSJBPMDB]    Script Date: 12/5/2016 2:47:59 PM ******/
CREATE DATABASE [LRSJBPMDB]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'LRSJBPMDB', FILENAME = N'c:\Program Files\Microsoft SQL Server\MSSQL13.ONEMORETHING\MSSQL\DATA\LRSJBPMDB.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'LRSJBPMDB_log', FILENAME = N'c:\Program Files\Microsoft SQL Server\MSSQL13.ONEMORETHING\MSSQL\DATA\LRSJBPMDB_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
GO

ALTER DATABASE [LRSJBPMDB] SET COMPATIBILITY_LEVEL = 130
GO

IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [LRSJBPMDB].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO

ALTER DATABASE [LRSJBPMDB] SET ANSI_NULL_DEFAULT OFF 
GO

ALTER DATABASE [LRSJBPMDB] SET ANSI_NULLS OFF 
GO

ALTER DATABASE [LRSJBPMDB] SET ANSI_PADDING OFF 
GO

ALTER DATABASE [LRSJBPMDB] SET ANSI_WARNINGS OFF 
GO

ALTER DATABASE [LRSJBPMDB] SET ARITHABORT OFF 
GO

ALTER DATABASE [LRSJBPMDB] SET AUTO_CLOSE OFF 
GO

ALTER DATABASE [LRSJBPMDB] SET AUTO_SHRINK OFF 
GO

ALTER DATABASE [LRSJBPMDB] SET AUTO_UPDATE_STATISTICS ON 
GO

ALTER DATABASE [LRSJBPMDB] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO

ALTER DATABASE [LRSJBPMDB] SET CURSOR_DEFAULT  GLOBAL 
GO

ALTER DATABASE [LRSJBPMDB] SET CONCAT_NULL_YIELDS_NULL OFF 
GO

ALTER DATABASE [LRSJBPMDB] SET NUMERIC_ROUNDABORT OFF 
GO

ALTER DATABASE [LRSJBPMDB] SET QUOTED_IDENTIFIER OFF 
GO

ALTER DATABASE [LRSJBPMDB] SET RECURSIVE_TRIGGERS OFF 
GO

ALTER DATABASE [LRSJBPMDB] SET  DISABLE_BROKER 
GO

ALTER DATABASE [LRSJBPMDB] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO

ALTER DATABASE [LRSJBPMDB] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO

ALTER DATABASE [LRSJBPMDB] SET TRUSTWORTHY OFF 
GO

ALTER DATABASE [LRSJBPMDB] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO

ALTER DATABASE [LRSJBPMDB] SET PARAMETERIZATION SIMPLE 
GO

ALTER DATABASE [LRSJBPMDB] SET READ_COMMITTED_SNAPSHOT OFF 
GO

ALTER DATABASE [LRSJBPMDB] SET HONOR_BROKER_PRIORITY OFF 
GO

ALTER DATABASE [LRSJBPMDB] SET RECOVERY FULL 
GO

ALTER DATABASE [LRSJBPMDB] SET  MULTI_USER 
GO

ALTER DATABASE [LRSJBPMDB] SET PAGE_VERIFY CHECKSUM  
GO

ALTER DATABASE [LRSJBPMDB] SET DB_CHAINING OFF 
GO

ALTER DATABASE [LRSJBPMDB] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO

ALTER DATABASE [LRSJBPMDB] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO

ALTER DATABASE [LRSJBPMDB] SET DELAYED_DURABILITY = DISABLED 
GO

ALTER DATABASE [LRSJBPMDB] SET QUERY_STORE = OFF
GO

USE [LRSJBPMDB]
GO

ALTER DATABASE SCOPED CONFIGURATION SET MAXDOP = 0;
GO

ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET MAXDOP = PRIMARY;
GO

ALTER DATABASE SCOPED CONFIGURATION SET LEGACY_CARDINALITY_ESTIMATION = OFF;
GO

ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET LEGACY_CARDINALITY_ESTIMATION = PRIMARY;
GO

ALTER DATABASE SCOPED CONFIGURATION SET PARAMETER_SNIFFING = ON;
GO

ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET PARAMETER_SNIFFING = PRIMARY;
GO

ALTER DATABASE SCOPED CONFIGURATION SET QUERY_OPTIMIZER_HOTFIXES = OFF;
GO

ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET QUERY_OPTIMIZER_HOTFIXES = PRIMARY;
GO

ALTER DATABASE [LRSJBPMDB] SET  READ_WRITE 
GO

-- Create login

USE [LRSJBPMDB]
GO

CREATE LOGIN [lrsjbpm] WITH PASSWORD=N'lrs#12345', DEFAULT_DATABASE=[LRSJBPMDB], DEFAULT_LANGUAGE=[us_english], CHECK_EXPIRATION=OFF, CHECK_POLICY=OFF
GO

CREATE USER [lrsjbpm] FOR LOGIN [lrsjbpm]
GO

ALTER ROLE [db_owner] ADD MEMBER [lrsjbpm]
GO

-- Create tables

create table Attachment (
    id bigint identity not null,
    accessType int,
    attachedAt datetime2,
    attachmentContentId bigint not null,
    contentType varchar(255),
    name varchar(255),
    attachment_size int,
    attachedBy_id varchar(255),
    TaskData_Attachments_Id bigint,
    primary key (id)
);

create table AuditTaskImpl (
    id bigint identity not null,
    activationTime datetime2,
    actualOwner varchar(255),
    createdBy varchar(255),
    createdOn datetime2,
    deploymentId varchar(255),
    description varchar(255),
    dueDate datetime2,
    name varchar(255),
    parentId bigint not null,
    priority int not null,
    processId varchar(255),
    processInstanceId bigint not null,
    processSessionId bigint not null,
    status varchar(255),
    taskId bigint,
    workItemId bigint,
    primary key (id)
);

create table BAMTaskSummary (
    pk bigint identity not null,
    createdDate datetime2,
    duration bigint,
    endDate datetime2,
    processInstanceId bigint not null,
    startDate datetime2,
    status varchar(255),
    taskId bigint not null,
    taskName varchar(255),
    userId varchar(255),
    OPTLOCK int,
    primary key (pk)
);

create table BooleanExpression (
    id bigint identity not null,
    expression varchar(MAX),
    type varchar(255),
    Escalation_Constraints_Id bigint,
    primary key (id)
);

create table Content (
    id bigint identity not null,
    content varbinary(MAX),
    primary key (id)
);

create table ContextMappingInfo (
    mappingId bigint identity not null,
    CONTEXT_ID varchar(255) not null,
    KSESSION_ID bigint not null,
    OWNER_ID varchar(255),
    OPTLOCK int,
    primary key (mappingId)
);

create table CorrelationKeyInfo (
    keyId bigint identity not null,
    name varchar(255),
    processInstanceId bigint not null,
    OPTLOCK int,
    primary key (keyId)
);

create table CorrelationPropertyInfo (
    propertyId bigint identity not null,
    name varchar(255),
    value varchar(255),
    OPTLOCK int,
    correlationKey_keyId bigint,
    primary key (propertyId)
);

create table Deadline (
    id bigint identity not null,
    deadline_date datetime2,
    escalated smallint,
    Deadlines_StartDeadLine_Id bigint,
    Deadlines_EndDeadLine_Id bigint,
    primary key (id)
);

create table Delegation_delegates (
    task_id bigint not null,
    entity_id varchar(255) not null
);

create table DeploymentStore (
    id bigint identity not null,
    attributes varchar(255),
    DEPLOYMENT_ID varchar(255),
    deploymentUnit varchar(MAX),
    state int,
    updateDate datetime2,
    primary key (id)
);

create table ErrorInfo (
    id bigint identity not null,
    message varchar(255),
    stacktrace varchar(5000),
    timestamp datetime2,
    REQUEST_ID bigint not null,
    primary key (id)
);

create table Escalation (
    id bigint identity not null,
    name varchar(255),
    Deadline_Escalation_Id bigint,
    primary key (id)
);

create table EventTypes (
    InstanceId bigint not null,
    element varchar(255)
);

create table I18NText (
    id bigint identity not null,
    language varchar(255),
    shortText varchar(255),
    text varchar(MAX),
    Task_Subjects_Id bigint,
    Task_Names_Id bigint,
    Task_Descriptions_Id bigint,
    Reassignment_Documentation_Id bigint,
    Notification_Subjects_Id bigint,
    Notification_Names_Id bigint,
    Notification_Documentation_Id bigint,
    Notification_Descriptions_Id bigint,
    Deadline_Documentation_Id bigint,
    primary key (id)
);

create table NodeInstanceLog (
    id bigint identity not null,
    connection varchar(255),
    log_date datetime2,
    externalId varchar(255),
    nodeId varchar(255),
    nodeInstanceId varchar(255),
    nodeName varchar(255),
    nodeType varchar(255),
    processId varchar(255),
    processInstanceId bigint not null,
    type int not null,
    workItemId bigint,
    primary key (id)
);

create table Notification (
    DTYPE varchar(31) not null,
    id bigint identity not null,
    priority int not null,
    Escalation_Notifications_Id bigint,
    primary key (id)
);

create table Notification_BAs (
    task_id bigint not null,
    entity_id varchar(255) not null
);

create table Notification_Recipients (
    task_id bigint not null,
    entity_id varchar(255) not null
);

create table Notification_email_header (
    Notification_id bigint not null,
    emailHeaders_id bigint not null,
    mapkey varchar(255) not null,
    primary key (Notification_id, mapkey)
);

create table OrganizationalEntity (
    DTYPE varchar(31) not null,
    id varchar(255) not null,
    primary key (id)
);

create table PeopleAssignments_BAs (
    task_id bigint not null,
    entity_id varchar(255) not null
);

create table PeopleAssignments_ExclOwners (
    task_id bigint not null,
    entity_id varchar(255) not null
);

create table PeopleAssignments_PotOwners (
    task_id bigint not null,
    entity_id varchar(255) not null
);

create table PeopleAssignments_Recipients (
    task_id bigint not null,
    entity_id varchar(255) not null
);

create table PeopleAssignments_Stakeholders (
    task_id bigint not null,
    entity_id varchar(255) not null
);

create table ProcessInstanceInfo (
    InstanceId bigint identity not null,
    lastModificationDate datetime2,
    lastReadDate datetime2,
    processId varchar(255),
    processInstanceByteArray varbinary(MAX),
    startDate datetime2,
    state int not null,
    OPTLOCK int,
    primary key (InstanceId)
);

create table ProcessInstanceLog (
    id bigint identity not null,
    correlationKey varchar(255),
    duration bigint,
    end_date datetime2,
    externalId varchar(255),
    user_identity varchar(255),
    outcome varchar(255),
    parentProcessInstanceId bigint,
    processId varchar(255),
    processInstanceDescription varchar(255),
    processInstanceId bigint not null,
    processName varchar(255),
    processVersion varchar(255),
    start_date datetime2,
    status int,
    primary key (id)
);

create table QueryDefinitionStore (
    id bigint identity not null,
    qExpression varchar(MAX),
    qName varchar(255),
    qSource varchar(255),
    qTarget varchar(255),
    primary key (id)
);

create table Reassignment (
    id bigint identity not null,
    Escalation_Reassignments_Id bigint,
    primary key (id)
);

create table Reassignment_potentialOwners (
    task_id bigint not null,
    entity_id varchar(255) not null
);

create table RequestInfo (
    id bigint identity not null,
    commandName varchar(255),
    deploymentId varchar(255),
    executions int not null,
    businessKey varchar(255),
    message varchar(255),
    owner varchar(255),
    requestData varbinary(MAX),
    responseData varbinary(MAX),
    retries int not null,
    status varchar(255),
    timestamp datetime2,
    primary key (id)
);

create table SessionInfo (
    id bigint identity not null,
    lastModificationDate datetime2,
    rulesByteArray varbinary(MAX),
    startDate datetime2,
    OPTLOCK int,
    primary key (id)
);

create table Task (
    id bigint identity not null,
    archived smallint,
    allowedToDelegate varchar(255),
    description varchar(255),
    formName varchar(255),
    name varchar(255),
    priority int not null,
    subTaskStrategy varchar(255),
    subject varchar(255),
    activationTime datetime2,
    createdOn datetime2,
    deploymentId varchar(255),
    documentAccessType int,
    documentContentId bigint not null,
    documentType varchar(255),
    expirationTime datetime2,
    faultAccessType int,
    faultContentId bigint not null,
    faultName varchar(255),
    faultType varchar(255),
    outputAccessType int,
    outputContentId bigint not null,
    outputType varchar(255),
    parentId bigint not null,
    previousStatus int,
    processId varchar(255),
    processInstanceId bigint not null,
    processSessionId bigint not null,
    skipable bit not null,
    status varchar(255),
    workItemId bigint not null,
    taskType varchar(255),
    OPTLOCK int,
    taskInitiator_id varchar(255),
    actualOwner_id varchar(255),
    createdBy_id varchar(255),
    primary key (id)
);

create table TaskDef (
    id bigint identity not null,
    name varchar(255),
    priority int not null,
    primary key (id)
);

create table TaskEvent (
    id bigint identity not null,
    logTime datetime2,
    message varchar(255),
    processInstanceId bigint,
    taskId bigint,
    type varchar(255),
    userId varchar(255),
    OPTLOCK int,
    workItemId bigint,
    primary key (id)
);

create table TaskVariableImpl (
    id bigint identity not null,
    modificationDate datetime2,
    name varchar(255),
    processId varchar(255),
    processInstanceId bigint,
    taskId bigint,
    type int,
    value varchar(4000),
    primary key (id)
);

create table VariableInstanceLog (
    id bigint identity not null,
    log_date datetime2,
    externalId varchar(255),
    oldValue varchar(255),
    processId varchar(255),
    processInstanceId bigint not null,
    value varchar(255),
    variableId varchar(255),
    variableInstanceId varchar(255),
    primary key (id)
);

create table WorkItemInfo (
    workItemId bigint identity not null,
    creationDate datetime2,
    name varchar(255),
    processInstanceId bigint not null,
    state bigint not null,
    OPTLOCK int,
    workItemByteArray varbinary(MAX),
    primary key (workItemId)
);

create table email_header (
    id bigint identity not null,
    body varchar(MAX),
    fromAddress varchar(255),
    language varchar(255),
    replyToAddress varchar(255),
    subject varchar(255),
    primary key (id)
);

create table task_comment (
    id bigint identity not null,
    addedAt datetime2,
    text varchar(MAX),
    addedBy_id varchar(255),
    TaskData_Comments_Id bigint,
    primary key (id)
);

alter table DeploymentStore 
    add constraint UK_85rgskt09thd8mkkfl3tb0y81 unique (DEPLOYMENT_ID);

alter table Notification_email_header 
    add constraint UK_ptaka5kost68h7l3wflv7w6y8 unique (emailHeaders_id);

alter table QueryDefinitionStore 
    add constraint UK_4ry5gt77jvq0orfttsoghta2j unique (qName);

alter table Attachment 
    add constraint FK_7ndpfa311i50bq7hy18q05va3 
    foreign key (attachedBy_id) 
    references OrganizationalEntity;

alter table Attachment 
    add constraint FK_hqupx569krp0f0sgu9kh87513 
    foreign key (TaskData_Attachments_Id) 
    references Task;

alter table BooleanExpression 
    add constraint FK_394nf2qoc0k9ok6omgd6jtpso 
    foreign key (Escalation_Constraints_Id) 
    references Escalation;

alter table CorrelationPropertyInfo 
    add constraint FK_hrmx1m882cejwj9c04ixh50i4 
    foreign key (correlationKey_keyId) 
    references CorrelationKeyInfo;

alter table Deadline 
    add constraint FK_68w742sge00vco2cq3jhbvmgx 
    foreign key (Deadlines_StartDeadLine_Id) 
    references Task;

alter table Deadline 
    add constraint FK_euoohoelbqvv94d8a8rcg8s5n 
    foreign key (Deadlines_EndDeadLine_Id) 
    references Task;

alter table Delegation_delegates 
    add constraint FK_gn7ula51sk55wj1o1m57guqxb 
    foreign key (entity_id) 
    references OrganizationalEntity;

alter table Delegation_delegates 
    add constraint FK_fajq6kossbsqwr3opkrctxei3 
    foreign key (task_id) 
    references Task;

alter table ErrorInfo 
    add constraint FK_cms0met37ggfw5p5gci3otaq0 
    foreign key (REQUEST_ID) 
    references RequestInfo;

alter table Escalation 
    add constraint FK_ay2gd4fvl9yaapviyxudwuvfg 
    foreign key (Deadline_Escalation_Id) 
    references Deadline;

alter table EventTypes 
    add constraint FK_nrecj4617iwxlc65ij6m7lsl1 
    foreign key (InstanceId) 
    references ProcessInstanceInfo;

alter table I18NText 
    add constraint FK_k16jpgrh67ti9uedf6konsu1p 
    foreign key (Task_Subjects_Id) 
    references Task;

alter table I18NText 
    add constraint FK_fd9uk6hemv2dx1ojovo7ms3vp 
    foreign key (Task_Names_Id) 
    references Task;

alter table I18NText 
    add constraint FK_4eyfp69ucrron2hr7qx4np2fp 
    foreign key (Task_Descriptions_Id) 
    references Task;

alter table I18NText 
    add constraint FK_pqarjvvnwfjpeyb87yd7m0bfi 
    foreign key (Reassignment_Documentation_Id) 
    references Reassignment;

alter table I18NText 
    add constraint FK_o84rkh69r47ti8uv4eyj7bmo2 
    foreign key (Notification_Subjects_Id) 
    references Notification;

alter table I18NText 
    add constraint FK_g1trxri8w64enudw2t1qahhk5 
    foreign key (Notification_Names_Id) 
    references Notification;

alter table I18NText 
    add constraint FK_qoce92c70adem3ccb3i7lec8x 
    foreign key (Notification_Documentation_Id) 
    references Notification;

alter table I18NText 
    add constraint FK_bw8vmpekejxt1ei2ge26gdsry 
    foreign key (Notification_Descriptions_Id) 
    references Notification;

alter table I18NText 
    add constraint FK_21qvifarxsvuxeaw5sxwh473w 
    foreign key (Deadline_Documentation_Id) 
    references Deadline;

alter table Notification 
    add constraint FK_bdbeml3768go5im41cgfpyso9 
    foreign key (Escalation_Notifications_Id) 
    references Escalation;

alter table Notification_BAs 
    add constraint FK_mfbsnbrhth4rjhqc2ud338s4i 
    foreign key (entity_id) 
    references OrganizationalEntity;

alter table Notification_BAs 
    add constraint FK_fc0uuy76t2bvxaxqysoo8xts7 
    foreign key (task_id) 
    references Notification;

alter table Notification_Recipients 
    add constraint FK_blf9jsrumtrthdaqnpwxt25eu 
    foreign key (entity_id) 
    references OrganizationalEntity;

alter table Notification_Recipients 
    add constraint FK_3l244pj8sh78vtn9imaymrg47 
    foreign key (task_id) 
    references Notification;

alter table Notification_email_header 
    add constraint FK_ptaka5kost68h7l3wflv7w6y8 
    foreign key (emailHeaders_id) 
    references email_header;

alter table Notification_email_header 
    add constraint FK_eth4nvxn21fk1vnju85vkjrai 
    foreign key (Notification_id) 
    references Notification;

alter table PeopleAssignments_BAs 
    add constraint FK_t38xbkrq6cppifnxequhvjsl2 
    foreign key (entity_id) 
    references OrganizationalEntity;

alter table PeopleAssignments_BAs 
    add constraint FK_omjg5qh7uv8e9bolbaq7hv6oh 
    foreign key (task_id) 
    references Task;

alter table PeopleAssignments_ExclOwners 
    add constraint FK_pth28a73rj6bxtlfc69kmqo0a 
    foreign key (entity_id) 
    references OrganizationalEntity;

alter table PeopleAssignments_ExclOwners 
    add constraint FK_b8owuxfrdng050ugpk0pdowa7 
    foreign key (task_id) 
    references Task;

alter table PeopleAssignments_PotOwners 
    add constraint FK_tee3ftir7xs6eo3fdvi3xw026 
    foreign key (entity_id) 
    references OrganizationalEntity;

alter table PeopleAssignments_PotOwners 
    add constraint FK_4dv2oji7pr35ru0w45trix02x 
    foreign key (task_id) 
    references Task;

alter table PeopleAssignments_Recipients 
    add constraint FK_4g7y3wx6gnokf6vycgpxs83d6 
    foreign key (entity_id) 
    references OrganizationalEntity;

alter table PeopleAssignments_Recipients 
    add constraint FK_enhk831fghf6akjilfn58okl4 
    foreign key (task_id) 
    references Task;

alter table PeopleAssignments_Stakeholders 
    add constraint FK_met63inaep6cq4ofb3nnxi4tm 
    foreign key (entity_id) 
    references OrganizationalEntity;

alter table PeopleAssignments_Stakeholders 
    add constraint FK_4bh3ay74x6ql9usunubttfdf1 
    foreign key (task_id) 
    references Task;

alter table Reassignment 
    add constraint FK_pnpeue9hs6kx2ep0sp16b6kfd 
    foreign key (Escalation_Reassignments_Id) 
    references Escalation;

alter table Reassignment_potentialOwners 
    add constraint FK_8frl6la7tgparlnukhp8xmody 
    foreign key (entity_id) 
    references OrganizationalEntity;

alter table Reassignment_potentialOwners 
    add constraint FK_qbega5ncu6b9yigwlw55aeijn 
    foreign key (task_id) 
    references Reassignment;

alter table Task 
    add constraint FK_dpk0f9ucm14c78bsxthh7h8yh 
    foreign key (taskInitiator_id) 
    references OrganizationalEntity;

alter table Task 
    add constraint FK_nh9nnt47f3l61qjlyedqt05rf 
    foreign key (actualOwner_id) 
    references OrganizationalEntity;

alter table Task 
    add constraint FK_k02og0u71obf1uxgcdjx9rcjc 
    foreign key (createdBy_id) 
    references OrganizationalEntity;

alter table task_comment 
    add constraint FK_aax378yjnsmw9kb9vsu994jjv 
    foreign key (addedBy_id) 
    references OrganizationalEntity;

alter table task_comment 
    add constraint FK_1ws9jdmhtey6mxu7jb0r0ufvs 
    foreign key (TaskData_Comments_Id) 
    references Task;

        
create index IDX_Attachment_Id ON Attachment(attachedBy_id);
create index IDX_Attachment_DataId ON Attachment(TaskData_Attachments_Id);
create index IDX_BoolExpr_Id ON BooleanExpression(Escalation_Constraints_Id);
create index IDX_CorrPropInfo_Id ON CorrelationPropertyInfo(correlationKey_keyId);
create index IDX_Deadline_StartId ON Deadline(Deadlines_StartDeadLine_Id);
create index IDX_Deadline_EndId ON Deadline(Deadlines_EndDeadLine_Id);
create index IDX_Delegation_EntityId ON Delegation_delegates(entity_id);
create index IDX_Delegation_TaskId ON Delegation_delegates(task_id);
create index IDX_ErrorInfo_Id ON ErrorInfo(REQUEST_ID);
create index IDX_Escalation_Id ON Escalation(Deadline_Escalation_Id);
create index IDX_EventTypes_Id ON EventTypes(InstanceId);
create index IDX_I18NText_SubjId ON I18NText(Task_Subjects_Id);
create index IDX_I18NText_NameId ON I18NText(Task_Names_Id);
create index IDX_I18NText_DescrId ON I18NText(Task_Descriptions_Id);
create index IDX_I18NText_ReassignId ON I18NText(Reassignment_Documentation_Id);
create index IDX_I18NText_NotSubjId ON I18NText(Notification_Subjects_Id);
create index IDX_I18NText_NotNamId ON I18NText(Notification_Names_Id);
create index IDX_I18NText_NotDocId ON I18NText(Notification_Documentation_Id);
create index IDX_I18NText_NotDescrId ON I18NText(Notification_Descriptions_Id);
create index IDX_I18NText_DeadDocId ON I18NText(Deadline_Documentation_Id);
create index IDX_Not_EscId ON Notification(Escalation_Notifications_Id);
create index IDX_NotBAs_Entity ON Notification_BAs(entity_id);
create index IDX_NotBAs_Task ON Notification_BAs(task_id);
create index IDX_NotRec_Entity ON Notification_Recipients(entity_id);
create index IDX_NotRec_Task ON Notification_Recipients(task_id);
create index IDX_NotEmail_Header ON Notification_email_header(emailHeaders_id);
create index IDX_NotEmail_Not ON Notification_email_header(Notification_id);
create index IDX_PAsBAs_Entity ON PeopleAssignments_BAs(entity_id);
create index IDX_PAsBAs_Task ON PeopleAssignments_BAs(task_id);
create index IDX_PAsExcl_Entity ON PeopleAssignments_ExclOwners(entity_id);
create index IDX_PAsExcl_Task ON PeopleAssignments_ExclOwners(task_id);
create index IDX_PAsPot_Entity ON PeopleAssignments_PotOwners(entity_id);
create index IDX_PAsPot_Task ON PeopleAssignments_PotOwners(task_id);
create index IDX_PAsRecip_Entity ON PeopleAssignments_Recipients(entity_id);
create index IDX_PAsRecip_Task ON PeopleAssignments_Recipients(task_id);
create index IDX_PAsStake_Entity ON PeopleAssignments_Stakeholders(entity_id);
create index IDX_PAsStake_Task ON PeopleAssignments_Stakeholders(task_id);
create index IDX_Reassign_Esc ON Reassignment(Escalation_Reassignments_Id);
create index IDX_ReassignPO_Entity ON Reassignment_potentialOwners(entity_id);
create index IDX_ReassignPO_Task ON Reassignment_potentialOwners(task_id);
create index IDX_Task_Initiator ON Task(taskInitiator_id);
create index IDX_Task_ActualOwner ON Task(actualOwner_id);
create index IDX_Task_CreatedBy ON Task(createdBy_id);
create index IDX_TaskComments_CreatedBy ON task_comment(addedBy_id);
create index IDX_TaskComments_Id ON task_comment(TaskData_Comments_Id);
        
create index IDX_Task_processInstanceId on Task(processInstanceId);
create index IDX_Task_processId on Task(processId);
create index IDX_Task_status on Task(status);
create index IDX_Task_archived on Task(archived);
create index IDX_Task_workItemId on Task(workItemId);
    
create index IDX_EventTypes_element ON EventTypes(element);

create index IDX_CMI_Context ON ContextMappingInfo(CONTEXT_ID);    
create index IDX_CMI_KSession ON ContextMappingInfo(KSESSION_ID);    
create index IDX_CMI_Owner ON ContextMappingInfo(OWNER_ID);
    
create index IDX_RequestInfo_status ON RequestInfo(status);
create index IDX_RequestInfo_timestamp ON RequestInfo(timestamp);
create index IDX_RequestInfo_owner ON RequestInfo(owner);
    
create index IDX_BAMTaskSumm_createdDate on BAMTaskSummary(createdDate);
create index IDX_BAMTaskSumm_duration on BAMTaskSummary(duration);
create index IDX_BAMTaskSumm_endDate on BAMTaskSummary(endDate);
create index IDX_BAMTaskSumm_pInstId on BAMTaskSummary(processInstanceId);
create index IDX_BAMTaskSumm_startDate on BAMTaskSummary(startDate);
create index IDX_BAMTaskSumm_status on BAMTaskSummary(status);
create index IDX_BAMTaskSumm_taskId on BAMTaskSummary(taskId);
create index IDX_BAMTaskSumm_taskName on BAMTaskSummary(taskName);
create index IDX_BAMTaskSumm_userId on BAMTaskSummary(userId);
    
create index IDX_PInstLog_duration on ProcessInstanceLog(duration);
create index IDX_PInstLog_end_date on ProcessInstanceLog(end_date);
create index IDX_PInstLog_extId on ProcessInstanceLog(externalId);
create index IDX_PInstLog_user_identity on ProcessInstanceLog(user_identity);
create index IDX_PInstLog_outcome on ProcessInstanceLog(outcome);
create index IDX_PInstLog_parentPInstId on ProcessInstanceLog(parentProcessInstanceId);
create index IDX_PInstLog_pId on ProcessInstanceLog(processId);
create index IDX_PInstLog_pInsteDescr on ProcessInstanceLog(processInstanceDescription);
create index IDX_PInstLog_pInstId on ProcessInstanceLog(processInstanceId);
create index IDX_PInstLog_pName on ProcessInstanceLog(processName);
create index IDX_PInstLog_pVersion on ProcessInstanceLog(processVersion);
create index IDX_PInstLog_start_date on ProcessInstanceLog(start_date);
create index IDX_PInstLog_status on ProcessInstanceLog(status);
create index IDX_PInstLog_correlation on ProcessInstanceLog(correlationKey);

CREATE TABLE [dbo].[QRTZ_CALENDARS] (
  [CALENDAR_NAME] [VARCHAR] (200)  NOT NULL ,
  [CALENDAR] [IMAGE] NOT NULL
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[QRTZ_CRON_TRIGGERS] (
  [TRIGGER_NAME] [VARCHAR] (200)  NOT NULL ,
  [TRIGGER_GROUP] [VARCHAR] (200)  NOT NULL ,
  [CRON_EXPRESSION] [VARCHAR] (120)  NOT NULL ,
  [TIME_ZONE_ID] [VARCHAR] (80) 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[QRTZ_FIRED_TRIGGERS] (
  [ENTRY_ID] [VARCHAR] (95)  NOT NULL ,
  [TRIGGER_NAME] [VARCHAR] (200)  NOT NULL ,
  [TRIGGER_GROUP] [VARCHAR] (200)  NOT NULL ,
  [IS_VOLATILE] [VARCHAR] (1)  NOT NULL ,
  [INSTANCE_NAME] [VARCHAR] (200)  NOT NULL ,
  [FIRED_TIME] [BIGINT] NOT NULL ,
  [PRIORITY] [INTEGER] NOT NULL ,
  [STATE] [VARCHAR] (16)  NOT NULL,
  [JOB_NAME] [VARCHAR] (200)  NULL ,
  [JOB_GROUP] [VARCHAR] (200)  NULL ,
  [IS_STATEFUL] [VARCHAR] (1)  NULL ,
  [REQUESTS_RECOVERY] [VARCHAR] (1)  NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[QRTZ_PAUSED_TRIGGER_GRPS] (
  [TRIGGER_GROUP] [VARCHAR] (200)  NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[QRTZ_SCHEDULER_STATE] (
  [INSTANCE_NAME] [VARCHAR] (200)  NOT NULL ,
  [LAST_CHECKIN_TIME] [BIGINT] NOT NULL ,
  [CHECKIN_INTERVAL] [BIGINT] NOT NULL
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[QRTZ_LOCKS] (
  [LOCK_NAME] [VARCHAR] (40)  NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[QRTZ_JOB_DETAILS] (
  [JOB_NAME] [VARCHAR] (200)  NOT NULL ,
  [JOB_GROUP] [VARCHAR] (200)  NOT NULL ,
  [DESCRIPTION] [VARCHAR] (250) NULL ,
  [JOB_CLASS_NAME] [VARCHAR] (250)  NOT NULL ,
  [IS_DURABLE] [VARCHAR] (1)  NOT NULL ,
  [IS_VOLATILE] [VARCHAR] (1)  NOT NULL ,
  [IS_STATEFUL] [VARCHAR] (1)  NOT NULL ,
  [REQUESTS_RECOVERY] [VARCHAR] (1)  NOT NULL ,
  [JOB_DATA] [IMAGE] NULL
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[QRTZ_JOB_LISTENERS] (
  [JOB_NAME] [VARCHAR] (200)  NOT NULL ,
  [JOB_GROUP] [VARCHAR] (200)  NOT NULL ,
  [JOB_LISTENER] [VARCHAR] (200)  NOT NULL
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[QRTZ_SIMPLE_TRIGGERS] (
  [TRIGGER_NAME] [VARCHAR] (200)  NOT NULL ,
  [TRIGGER_GROUP] [VARCHAR] (200)  NOT NULL ,
  [REPEAT_COUNT] [BIGINT] NOT NULL ,
  [REPEAT_INTERVAL] [BIGINT] NOT NULL ,
  [TIMES_TRIGGERED] [BIGINT] NOT NULL
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[QRTZ_BLOB_TRIGGERS] (
  [TRIGGER_NAME] [VARCHAR] (200)  NOT NULL ,
  [TRIGGER_GROUP] [VARCHAR] (200)  NOT NULL ,
  [BLOB_DATA] [IMAGE] NULL
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[QRTZ_TRIGGER_LISTENERS] (
  [TRIGGER_NAME] [VARCHAR] (200)  NOT NULL ,
  [TRIGGER_GROUP] [VARCHAR] (200)  NOT NULL ,
  [TRIGGER_LISTENER] [VARCHAR] (200)  NOT NULL
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[QRTZ_TRIGGERS] (
  [TRIGGER_NAME] [VARCHAR] (200)  NOT NULL ,
  [TRIGGER_GROUP] [VARCHAR] (200)  NOT NULL ,
  [JOB_NAME] [VARCHAR] (200)  NOT NULL ,
  [JOB_GROUP] [VARCHAR] (200)  NOT NULL ,
  [IS_VOLATILE] [VARCHAR] (1)  NOT NULL ,
  [DESCRIPTION] [VARCHAR] (250) NULL ,
  [NEXT_FIRE_TIME] [BIGINT] NULL ,
  [PREV_FIRE_TIME] [BIGINT] NULL ,
  [PRIORITY] [INTEGER] NULL ,
  [TRIGGER_STATE] [VARCHAR] (16)  NOT NULL ,
  [TRIGGER_TYPE] [VARCHAR] (8)  NOT NULL ,
  [START_TIME] [BIGINT] NOT NULL ,
  [END_TIME] [BIGINT] NULL ,
  [CALENDAR_NAME] [VARCHAR] (200)  NULL ,
  [MISFIRE_INSTR] [SMALLINT] NULL ,
  [JOB_DATA] [IMAGE] NULL
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[QRTZ_CALENDARS] WITH NOCHECK ADD
  CONSTRAINT [PK_QRTZ_CALENDARS] PRIMARY KEY  CLUSTERED
  (
    [CALENDAR_NAME]
  )  ON [PRIMARY]
GO

ALTER TABLE [dbo].[QRTZ_CRON_TRIGGERS] WITH NOCHECK ADD
  CONSTRAINT [PK_QRTZ_CRON_TRIGGERS] PRIMARY KEY  CLUSTERED
  (
    [TRIGGER_NAME],
    [TRIGGER_GROUP]
  )  ON [PRIMARY]
GO

ALTER TABLE [dbo].[QRTZ_FIRED_TRIGGERS] WITH NOCHECK ADD
  CONSTRAINT [PK_QRTZ_FIRED_TRIGGERS] PRIMARY KEY  CLUSTERED
  (
    [ENTRY_ID]
  )  ON [PRIMARY]
GO

ALTER TABLE [dbo].[QRTZ_PAUSED_TRIGGER_GRPS] WITH NOCHECK ADD
  CONSTRAINT [PK_QRTZ_PAUSED_TRIGGER_GRPS] PRIMARY KEY  CLUSTERED
  (
    [TRIGGER_GROUP]
  )  ON [PRIMARY]
GO

ALTER TABLE [dbo].[QRTZ_SCHEDULER_STATE] WITH NOCHECK ADD
  CONSTRAINT [PK_QRTZ_SCHEDULER_STATE] PRIMARY KEY  CLUSTERED
  (
    [INSTANCE_NAME]
  )  ON [PRIMARY]
GO

ALTER TABLE [dbo].[QRTZ_LOCKS] WITH NOCHECK ADD
  CONSTRAINT [PK_QRTZ_LOCKS] PRIMARY KEY  CLUSTERED
  (
    [LOCK_NAME]
  )  ON [PRIMARY]
GO

ALTER TABLE [dbo].[QRTZ_JOB_DETAILS] WITH NOCHECK ADD
  CONSTRAINT [PK_QRTZ_JOB_DETAILS] PRIMARY KEY  CLUSTERED
  (
    [JOB_NAME],
    [JOB_GROUP]
  )  ON [PRIMARY]
GO

ALTER TABLE [dbo].[QRTZ_JOB_LISTENERS] WITH NOCHECK ADD
  CONSTRAINT [PK_QRTZ_JOB_LISTENERS] PRIMARY KEY  CLUSTERED
  (
    [JOB_NAME],
    [JOB_GROUP],
    [JOB_LISTENER]
  )  ON [PRIMARY]
GO

ALTER TABLE [dbo].[QRTZ_SIMPLE_TRIGGERS] WITH NOCHECK ADD
  CONSTRAINT [PK_QRTZ_SIMPLE_TRIGGERS] PRIMARY KEY  CLUSTERED
  (
    [TRIGGER_NAME],
    [TRIGGER_GROUP]
  )  ON [PRIMARY]
GO

ALTER TABLE [dbo].[QRTZ_TRIGGER_LISTENERS] WITH NOCHECK ADD
  CONSTRAINT [PK_QRTZ_TRIGGER_LISTENERS] PRIMARY KEY  CLUSTERED
  (
    [TRIGGER_NAME],
    [TRIGGER_GROUP],
    [TRIGGER_LISTENER]
  )  ON [PRIMARY]
GO

ALTER TABLE [dbo].[QRTZ_TRIGGERS] WITH NOCHECK ADD
  CONSTRAINT [PK_QRTZ_TRIGGERS] PRIMARY KEY  CLUSTERED
  (
    [TRIGGER_NAME],
    [TRIGGER_GROUP]
  )  ON [PRIMARY]
GO

ALTER TABLE [dbo].[QRTZ_CRON_TRIGGERS] ADD
  CONSTRAINT [FK_QRTZ_CRON_TRIGGERS_QRTZ_TRIGGERS] FOREIGN KEY
  (
    [TRIGGER_NAME],
    [TRIGGER_GROUP]
  ) REFERENCES [dbo].[QRTZ_TRIGGERS] (
    [TRIGGER_NAME],
    [TRIGGER_GROUP]
  ) ON DELETE CASCADE
GO

ALTER TABLE [dbo].[QRTZ_JOB_LISTENERS] ADD
  CONSTRAINT [FK_QRTZ_JOB_LISTENERS_QRTZ_JOB_DETAILS] FOREIGN KEY
  (
    [JOB_NAME],
    [JOB_GROUP]
  ) REFERENCES [dbo].[QRTZ_JOB_DETAILS] (
    [JOB_NAME],
    [JOB_GROUP]
  ) ON DELETE CASCADE
GO

ALTER TABLE [dbo].[QRTZ_SIMPLE_TRIGGERS] ADD
  CONSTRAINT [FK_QRTZ_SIMPLE_TRIGGERS_QRTZ_TRIGGERS] FOREIGN KEY
  (
    [TRIGGER_NAME],
    [TRIGGER_GROUP]
  ) REFERENCES [dbo].[QRTZ_TRIGGERS] (
    [TRIGGER_NAME],
    [TRIGGER_GROUP]
  ) ON DELETE CASCADE
GO

ALTER TABLE [dbo].[QRTZ_TRIGGER_LISTENERS] ADD
  CONSTRAINT [FK_QRTZ_TRIGGER_LISTENERS_QRTZ_TRIGGERS] FOREIGN KEY
  (
    [TRIGGER_NAME],
    [TRIGGER_GROUP]
  ) REFERENCES [dbo].[QRTZ_TRIGGERS] (
    [TRIGGER_NAME],
    [TRIGGER_GROUP]
  ) ON DELETE CASCADE
GO

ALTER TABLE [dbo].[QRTZ_TRIGGERS] ADD
  CONSTRAINT [FK_QRTZ_TRIGGERS_QRTZ_JOB_DETAILS] FOREIGN KEY
  (
    [JOB_NAME],
    [JOB_GROUP]
  ) REFERENCES [dbo].[QRTZ_JOB_DETAILS] (
    [JOB_NAME],
    [JOB_GROUP]
  )
GO

INSERT INTO [dbo].[QRTZ_LOCKS] VALUES('TRIGGER_ACCESS');
INSERT INTO [dbo].[QRTZ_LOCKS] VALUES('JOB_ACCESS');
INSERT INTO [dbo].[QRTZ_LOCKS] VALUES('CALENDAR_ACCESS');
INSERT INTO [dbo].[QRTZ_LOCKS] VALUES('STATE_ACCESS');
INSERT INTO [dbo].[QRTZ_LOCKS] VALUES('MISFIRE_ACCESS');
