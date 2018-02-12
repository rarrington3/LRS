/*
Crawls the application with each of the given users starting at the main
screen finding any dead links and running a series of canned tests on
each page to see if there are common issues, like errors and such.
 */

let domainRegExp = /^([A-z]{0,5}:)?\/\/([A-z0-9_\-\.:])\/?/i;

export default class Crawler {
    processed:Array = [];
    users:Array = [];
    resolutions:Array = [];

    login(user:Object):Promise {
        return new Promise(resolve => {
            resolve(user);
        });
    }

    isError(entry:Object):boolean {
        let level = entry.level.name || entry.level;
        return level === 'SEVERE' && !entry.message.includes('livereload.js');
    }

    testPage(page:string, user:Object, resolution:Array) {
        this.processed.push(page);
        describe(`crawling page "${page}" with user ${JSON.stringify(user)} @ resolution ${JSON.stringify(resolution)}`, () => {
            beforeEach(done => {
                //clear browser log
                browser.driver.manage().logs().get('browser').then(() => {
                    browser.driver.manage().window().setSize(resolution[0], resolution[1]).then(() => {
                        this.login(user).then(() => {
                            browser.get(page).then(done);
                        });
                    });
                });
            });
            it('should not have errors on page load', done => {
                browser.getCurrentUrl().then(url => {
                    let domain = url.match(domainRegExp)[2];
                    this.processed.push(url);
                    element.all(by.css('body [href]')).then(links => {
                        let promises = [];
                        links.forEach((link) => {
                            promises.push(link.getAttribute('href').then(link => {
                                let isProcessed = !!this.processed.find(p => p === link),
                                    isExternal = /^([A-z]{0,5}:)?\/\//i.test(link) && link.match(domainRegExp)[2] !== domain;
                                if (!isProcessed && !isExternal) {
                                    this.testPage(link, user, resolution);
                                }
                            }));
                        });
                        Promise.all(promises).then(done);
                    });
                });
                done();
            });
            afterEach(done => {
                browser.manage().logs().get('browser').then(browserLog => {
                    let errors = browserLog.filter(e => this.isError(e));
                    expect(JSON.stringify(errors, null, 2)).toBe('[]');
                    done();
                });
            });
        });

    }

    start(options:Object = {}) {
        Object.assign(this, options);
        this.processed = [];
        this.users.forEach(user => {
            this.resolutions.forEach(resolution => {
                this.testPage('', user, resolution);
            });
        });
    }
}
