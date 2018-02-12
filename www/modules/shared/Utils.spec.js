// This file was generated from the exports scaffold
// Copyright 2016

import Utils from './Utils';

import $ from 'jquery';

describe('shared/Utils.js', () => {

    let utils = new Utils();

    it('should return Utils instance', () => {
        expect(utils).toBeDefined();
    });

    it('should provide a standard way of handling navigational anchor clicks', function() {
        let $event = {preventDefault(){}};
        spyOn($event, 'preventDefault');
        spyOn($.fn, 'animate');
        spyOn($.fn, 'find').and.callThrough();
        Utils.handleAnchorClick($event, {nativeElement: '<div><a name="test"/></div>'}, 'test');
        expect($.fn.animate).toHaveBeenCalled();
        expect($.fn.find).toHaveBeenCalled();
        expect($event.preventDefault).toHaveBeenCalled();
    });

    it('should throw an error if parameters are not passed to handleAnchorClick', function() {
        expect( () => { Utils.handleAnchorClick(); } ).toThrow();
    });

});

