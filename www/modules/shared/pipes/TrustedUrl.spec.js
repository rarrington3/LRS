// This file was generated from the pipe scaffold
// Copyright 2017

import TrustedUrl from './TrustedUrl';

// This is marked as xdescribe because the sanitizer in the filter isn't being initialized.
xdescribe('shared/TrustedUrl.js', () => {

    let trustedUrl:TrustedUrl;

    beforeEach(() => {
        trustedUrl = new TrustedUrl();
    });

    it('should return formatted value', () => {

        let url = 'https://some-trusted-url';

        expect(trustedUrl.transform(url)).toBe(url);
    });

});
