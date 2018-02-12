// This file was generated from the service scaffold
// Copyright 2016

import {Injectable} from '@angular/core';
import {CanActivate} from '@angular/router';
import UserSvc from './UserSvc';

let preloader = require('../../preloader/main');

@Injectable()
class AuthGuard implements CanActivate {
    user: UserSvc;

    constructor(user: UserSvc) {
        this.user = user;
    }
}

class LoggedInGuard extends AuthGuard {
    canActivate() {
        return this.user && !!this.user.authorities;
    }
}

class ReviewerGuard extends AuthGuard {
    canActivate() {
        return this.user && !!this.user.authorities && this.user.authorities.includes('ROLE_REVIEWER');
    }
}

class LenderMonitoringGuard extends AuthGuard {
    canActivate() {
        return this.user && !!this.user.selectionReasonSkillCodes && this.user.selectionReasonSkillCodes.includes('LENDER_MONITORING');
    }
}

class ReviewerOrAdminGuard extends AuthGuard {
    canActivate() {
        return this.user && !!this.user.authorities && (
            this.user.authorities.includes('ROLE_REVIEWER') ||
            this.user.authorities.includes('ROLE_REVIEW_LOCATION_ADMIN') ||
            this.user.authorities.includes('ROLE_HQ_ADMIN'));
    }
}

class ProgramAssistantGuard extends AuthGuard {
    canActivate() {
        return this.user && !!this.user.authorities && this.user.authorities.includes('ROLE_PROGRAM_ASSISTANT');
    }
}

class ReviewLocationAdminGuard extends AuthGuard {
    canActivate() {
        return this.user && !!this.user.authorities && this.user.authorities.includes('ROLE_REVIEW_LOCATION_ADMIN');
    }
}

class HqAdminGuard extends AuthGuard {
    canActivate() {
        return this.user && !!this.user.authorities && this.user.authorities.includes('ROLE_HQ_ADMIN');
    }
}

class IndemnifierGuard extends AuthGuard {
    canActivate() {
        return this.user && !!this.user.authorities && this.user.authorities.includes('ROLE_INDEMNIFIER');
    }
}

class ResponseCoordinatorGuard extends AuthGuard {
    canActivate() {
        return this.user && !!this.user.authorities && (
            this.user.authorities.includes('ROLE_RESPONSE_COORDINATOR') ||
            this.user.authorities.includes('ROLE_INDEMNIFIER')
        );
    }
}

class LenderGuard extends AuthGuard {
    canActivate() {
        return this.user && !!this.user.authorities && (
            this.user.authorities.includes('ROLE_RESPONSE_COORDINATOR') ||
            this.user.authorities.includes('ROLE_INDEMNIFIER') ||
            this.user.authorities.includes('ROLE_LRS_READ_ONLY')
        );
    }
}

class BinderRequestGuard extends AuthGuard {
    canActivate() {
        return this.user && !!this.user.authorities && (this.user.authorities.includes('ROLE_HQ_ADMIN') || this.user.authorities.includes('ROLE_REVIEW_LOCATION_ADMIN') || this.user.authorities.includes('ROLE_PROGRAM_ASSISTANT'));
    }
}

class LrsReadOnlyGuard extends AuthGuard {
    canActivate() {
        return this.user && !!this.user.authorities && this.user.authorities.includes('ROLE_LRS_READ_ONLY');
    }
}

class ExceptionsGuard extends AuthGuard {
    canActivate() {
        return this.user && !!this.user.authorities && (
            this.user.authorities.includes('ROLE_HQ_ADMIN') ||
            this.user.authorities.includes('ROLE_REVIEW_LOCATION_ADMIN') ||
            this.user.authorities.includes('ROLE_PROGRAM_ASSISTANT'));
    }
}

class AdminGuard extends AuthGuard {
    canActivate() {
        return this.user && !!this.user.authorities && (this.user.authorities.includes('ROLE_HQ_ADMIN') || this.user.authorities.includes('ROLE_REVIEW_LOCATION_ADMIN'));
    }
}

class MicrostrategyGuard extends AuthGuard {
    canActivate() {
        return this.user && !!this.user.authorities && (this.user.authorities.includes('ROLE_HQ_ADMIN') || this.user.authorities.includes('ROLE_RESPONSE_COORDINATOR') || this.user.authorities.includes('ROLE_REVIEWER')
		|| this.user.authorities.includes('ROLE_REVIEW_LOCATION_ADMIN') || this.user.authorities.includes('ROLE_LRS_READ_ONLY') || this.user.authorities.includes('ROLE_INDEMNIFIER')
		);
    }
}

class DevModeGuard extends AuthGuard {
    canActivate() {
        return preloader.uiConfig.lrsUiDev;
    }
}

class AdminViewGuard extends AuthGuard {
    canActivate() {
        return this.user && !!this.user.authorities && (
            this.user.authorities.includes('ROLE_HQ_ADMIN') ||
            this.user.authorities.includes('ROLE_MONITOR_READ_ONLY')
        );
    }
}

export {
LoggedInGuard,
ReviewerGuard,
ReviewerOrAdminGuard,
ProgramAssistantGuard,
ReviewLocationAdminGuard,
HqAdminGuard,
IndemnifierGuard,
ResponseCoordinatorGuard,
LenderGuard,
BinderRequestGuard,
LrsReadOnlyGuard,
ExceptionsGuard,
AdminGuard,
MicrostrategyGuard,
DevModeGuard,
LenderMonitoringGuard,
AdminViewGuard
};
