import Crawler from './Crawler.e2e';

//TODO: change to false for angular
browser.ignoreSynchronization = true;

let crawler = new Crawler();
crawler.start({
    //TODO pass in some user objects, resolutions and login function
    users: [
        {username: 'test'}
    ],
    resolutions: [
        [1024, 768]
    ]/*,
    login: (user) => {
        return new Promise(resolve => {
           resolve(user);
        });
    }*/
});
