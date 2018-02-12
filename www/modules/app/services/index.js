export let APP_PROVIDERS = [];

// NOTE: expose any services from this module using the following pattern:
//      export SomeService from './SomeService';
//      APP_PROVIDERS.push(exports.SomeService);

export ModalSvc from './ModalSvc';
APP_PROVIDERS.push(exports.ModalSvc);

export ReviewerSvc from './ReviewerSvc';
APP_PROVIDERS.push(exports.ReviewerSvc);

export DictionarySvc from './DictionarySvc';
APP_PROVIDERS.push(exports.DictionarySvc);

export UserSvc from './UserSvc';
APP_PROVIDERS.push(exports.UserSvc);

export BatchService from './BatchService';
APP_PROVIDERS.push(exports.BatchService);

export { LoggedInGuard } from './AuthGuards';
APP_PROVIDERS.push(exports.LoggedInGuard);

export { ReviewerGuard } from './AuthGuards';
APP_PROVIDERS.push(exports.ReviewerGuard);

export { ReviewerOrAdminGuard } from './AuthGuards';
APP_PROVIDERS.push(exports.ReviewerOrAdminGuard);

export { ProgramAssistantGuard } from './AuthGuards';
APP_PROVIDERS.push(exports.ProgramAssistantGuard);

export { ReviewLocationAdminGuard } from './AuthGuards';
APP_PROVIDERS.push(exports.ReviewLocationAdminGuard);

export { HqAdminGuard } from './AuthGuards';
APP_PROVIDERS.push(exports.HqAdminGuard);

export { IndemnifierGuard } from './AuthGuards';
APP_PROVIDERS.push(exports.IndemnifierGuard);

export { ResponseCoordinatorGuard } from './AuthGuards';
APP_PROVIDERS.push(exports.ResponseCoordinatorGuard);

export { LenderGuard } from './AuthGuards';
APP_PROVIDERS.push(exports.LenderGuard);

export { LenderMonitoringGuard } from './AuthGuards';
APP_PROVIDERS.push(exports.LenderMonitoringGuard);

export { BinderRequestGuard } from './AuthGuards';
APP_PROVIDERS.push(exports.BinderRequestGuard);

export { LrsReadOnlyGuard } from './AuthGuards';
APP_PROVIDERS.push(exports.LrsReadOnlyGuard);

export { ExceptionsGuard } from './AuthGuards';
APP_PROVIDERS.push(exports.ExceptionsGuard);

export { AdminGuard } from './AuthGuards';
APP_PROVIDERS.push(exports.AdminGuard);

export { MicrostrategyGuard } from './AuthGuards';
APP_PROVIDERS.push(exports.MicrostrategyGuard);

export { DevModeGuard } from './AuthGuards';
APP_PROVIDERS.push(exports.DevModeGuard);

export { AdminViewGuard } from './AuthGuards';
APP_PROVIDERS.push(exports.AdminViewGuard);

export LocationSvc from './LocationSvc';
APP_PROVIDERS.push(exports.LocationSvc);

export SaveBarSvc from './SaveBarSvc';
APP_PROVIDERS.push(exports.SaveBarSvc);

export SaveGuard from './SaveGuard';
APP_PROVIDERS.push(exports.SaveGuard);


export CrossFilterService from './CrossFilterService';
APP_PROVIDERS.push(exports.CrossFilterService);
