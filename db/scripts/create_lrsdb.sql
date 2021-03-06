USE [master]
GO

/****** Object:  Database [LRSDB]    Script Date: 9/13/2016 2:48:13 PM ******/
CREATE DATABASE [LRSDB]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'LRSDB', FILENAME = N'c:\Program Files\Microsoft SQL Server\MSSQL13.ONEMORETHING\MSSQL\DATA\LRSDB.mdf' , SIZE = 153600KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'LRSDB_log', FILENAME = N'c:\Program Files\Microsoft SQL Server\MSSQL13.ONEMORETHING\MSSQL\DATA\LRSDB_log.ldf' , SIZE = 427392KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO

ALTER DATABASE [LRSDB] SET COMPATIBILITY_LEVEL = 120
GO

IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [LRSDB].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO

ALTER DATABASE [LRSDB] SET ANSI_NULL_DEFAULT OFF 
GO

ALTER DATABASE [LRSDB] SET ANSI_NULLS OFF 
GO

ALTER DATABASE [LRSDB] SET ANSI_PADDING OFF 
GO

ALTER DATABASE [LRSDB] SET ANSI_WARNINGS OFF 
GO

ALTER DATABASE [LRSDB] SET ARITHABORT OFF 
GO

ALTER DATABASE [LRSDB] SET AUTO_CLOSE OFF 
GO

ALTER DATABASE [LRSDB] SET AUTO_SHRINK OFF 
GO

ALTER DATABASE [LRSDB] SET AUTO_UPDATE_STATISTICS ON 
GO

ALTER DATABASE [LRSDB] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO

ALTER DATABASE [LRSDB] SET CURSOR_DEFAULT  GLOBAL 
GO

ALTER DATABASE [LRSDB] SET CONCAT_NULL_YIELDS_NULL OFF 
GO

ALTER DATABASE [LRSDB] SET NUMERIC_ROUNDABORT OFF 
GO

ALTER DATABASE [LRSDB] SET QUOTED_IDENTIFIER OFF 
GO

ALTER DATABASE [LRSDB] SET RECURSIVE_TRIGGERS OFF 
GO

ALTER DATABASE [LRSDB] SET  DISABLE_BROKER 
GO

ALTER DATABASE [LRSDB] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO

ALTER DATABASE [LRSDB] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO

ALTER DATABASE [LRSDB] SET TRUSTWORTHY OFF 
GO

ALTER DATABASE [LRSDB] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO

ALTER DATABASE [LRSDB] SET PARAMETERIZATION SIMPLE 
GO

ALTER DATABASE [LRSDB] SET READ_COMMITTED_SNAPSHOT OFF 
GO

ALTER DATABASE [LRSDB] SET HONOR_BROKER_PRIORITY OFF 
GO

ALTER DATABASE [LRSDB] SET RECOVERY FULL 
GO

ALTER DATABASE [LRSDB] SET  MULTI_USER 
GO

ALTER DATABASE [LRSDB] SET PAGE_VERIFY CHECKSUM  
GO

ALTER DATABASE [LRSDB] SET DB_CHAINING OFF 
GO

ALTER DATABASE [LRSDB] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO

ALTER DATABASE [LRSDB] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO

ALTER DATABASE [LRSDB] SET DELAYED_DURABILITY = DISABLED 
GO

ALTER DATABASE [LRSDB] SET QUERY_STORE = OFF
GO

USE [LRSDB]
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

ALTER DATABASE [LRSDB] SET  READ_WRITE 
GO

------------------------

/****** Object:  Database [LRSEXTDB]    Script Date: 9/13/2016 2:48:13 PM ******/
CREATE DATABASE [LRSEXTDB]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'LRSEXTDB', FILENAME = N'c:\Program Files\Microsoft SQL Server\MSSQL13.ONEMORETHING\MSSQL\DATA\LRSEXTDB.mdf' , SIZE = 153600KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'LRSEXTDB_log', FILENAME = N'c:\Program Files\Microsoft SQL Server\MSSQL13.ONEMORETHING\MSSQL\DATA\LRSEXTDB_log.ldf' , SIZE = 427392KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO

ALTER DATABASE [LRSEXTDB] SET COMPATIBILITY_LEVEL = 120
GO

IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [LRSEXTDB].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO

ALTER DATABASE [LRSEXTDB] SET ANSI_NULL_DEFAULT OFF 
GO

ALTER DATABASE [LRSEXTDB] SET ANSI_NULLS OFF 
GO

ALTER DATABASE [LRSEXTDB] SET ANSI_PADDING OFF 
GO

ALTER DATABASE [LRSEXTDB] SET ANSI_WARNINGS OFF 
GO

ALTER DATABASE [LRSEXTDB] SET ARITHABORT OFF 
GO

ALTER DATABASE [LRSEXTDB] SET AUTO_CLOSE OFF 
GO

ALTER DATABASE [LRSEXTDB] SET AUTO_SHRINK OFF 
GO

ALTER DATABASE [LRSEXTDB] SET AUTO_UPDATE_STATISTICS ON 
GO

ALTER DATABASE [LRSEXTDB] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO

ALTER DATABASE [LRSEXTDB] SET CURSOR_DEFAULT  GLOBAL 
GO

ALTER DATABASE [LRSEXTDB] SET CONCAT_NULL_YIELDS_NULL OFF 
GO

ALTER DATABASE [LRSEXTDB] SET NUMERIC_ROUNDABORT OFF 
GO

ALTER DATABASE [LRSEXTDB] SET QUOTED_IDENTIFIER OFF 
GO

ALTER DATABASE [LRSEXTDB] SET RECURSIVE_TRIGGERS OFF 
GO

ALTER DATABASE [LRSEXTDB] SET  DISABLE_BROKER 
GO

ALTER DATABASE [LRSEXTDB] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO

ALTER DATABASE [LRSEXTDB] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO

ALTER DATABASE [LRSEXTDB] SET TRUSTWORTHY OFF 
GO

ALTER DATABASE [LRSEXTDB] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO

ALTER DATABASE [LRSEXTDB] SET PARAMETERIZATION SIMPLE 
GO

ALTER DATABASE [LRSEXTDB] SET READ_COMMITTED_SNAPSHOT OFF 
GO

ALTER DATABASE [LRSEXTDB] SET HONOR_BROKER_PRIORITY OFF 
GO

ALTER DATABASE [LRSEXTDB] SET RECOVERY FULL 
GO

ALTER DATABASE [LRSEXTDB] SET  MULTI_USER 
GO

ALTER DATABASE [LRSEXTDB] SET PAGE_VERIFY CHECKSUM  
GO

ALTER DATABASE [LRSEXTDB] SET DB_CHAINING OFF 
GO

ALTER DATABASE [LRSEXTDB] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO

ALTER DATABASE [LRSEXTDB] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO

ALTER DATABASE [LRSEXTDB] SET DELAYED_DURABILITY = DISABLED 
GO

ALTER DATABASE [LRSEXTDB] SET QUERY_STORE = OFF
GO

USE [LRSEXTDB]
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

ALTER DATABASE [LRSEXTDB] SET  READ_WRITE 
GO

--create logins

USE [master]
GO

CREATE LOGIN [LRS_JAVA] WITH PASSWORD=N'Lrsdb_#1234', DEFAULT_DATABASE=[LRSDB], DEFAULT_LANGUAGE=[us_english], CHECK_EXPIRATION=OFF, CHECK_POLICY=ON
GO

CREATE LOGIN [LRS_BATCH] WITH PASSWORD=N'Lrsdb_#1234', DEFAULT_DATABASE=[LRSDB], DEFAULT_LANGUAGE=[us_english], CHECK_EXPIRATION=OFF, CHECK_POLICY=ON
GO

use [LRSDB]
go

create user LRS_JAVA for login LRS_JAVA
go

alter  role db_owner add member LRS_JAVA
go

create user LRS_BATCH for login LRS_BATCH
go

alter  role db_owner add member LRS_BATCH
go

use [LRSEXTDB]
go

create user LRS_JAVA for login LRS_JAVA
go

alter  role db_owner add member LRS_JAVA
go

create user LRS_BATCH for login LRS_BATCH
go

alter  role db_owner add member LRS_BATCH
go




