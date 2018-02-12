export let ADMIN_COMPONENTS = [];

// NOTE: expose any components from this module using the following pattern:
//      export SomeComponent from './SomeComponent';
//      ADMIN_COMPONENTS.push(exports.SomeComponent);

export QaTreeQuestionsEdit from './QaTreeQuestionsEdit';
ADMIN_COMPONENTS.push(exports.QaTreeQuestionsEdit);

export QaTreeQuestions from './QaTreeQuestions';
ADMIN_COMPONENTS.push(exports.QaTreeQuestions);
