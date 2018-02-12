// This file was generated from the exports scaffold
// Copyright 2016

import _ from 'lodash';

/**
 * @example
 * import Utils from 'modules/shared/utils/ArrayUtils';
 */
export default class ArrayUtils {
    static _sortBy = 'order';

    constructor() {
    }

    static sortUp(items: Array, item: any, sortBy: string) {

        let index = items.indexOf(item);
        if (index > 0) {
            let other = items[index - 1];
            ArrayUtils.invert(items, item, other, sortBy);
            return true;
        }
        return false;
    }

    static sortDown(items: Array, item: any, sortBy: string) {
        let index = items.indexOf(item);
        if (index < (items.length - 1)) {
            let other = items[index + 1];
            ArrayUtils.invert(items, item, other, sortBy);
            return true;
        }
        return false;
    }

    static invert(items: Array, item: any, other: any, sortBy: string) {
        sortBy = sortBy || ArrayUtils._sortBy;
        let index = items.indexOf(item);
        let otherIndex = items.indexOf(other);

        // if same order lets make sure it is initialized
        if (item[sortBy] === other[sortBy]) {
            items.forEach((item, i) => {
                item[sortBy] = (i + 1);
            });
        }

        let otherOrder = other[sortBy];
        other[sortBy] = item[sortBy];
        item[sortBy] = otherOrder;

        items[index] = other;
        items[otherIndex] = item;
    }

    static toArray(item: any) {
        return Object.keys(item).map(k => item[k]);
    }

    static remove(items: Array, item: any, sortBy:string) {
        sortBy = sortBy || ArrayUtils._sortBy;
        let index = items.indexOf(item);
        if (index >= 0) { // item exists
            items.splice(index, 1); // remove
            if (items.length && item[sortBy] !== undefined) { // check if items left & order exists
                items.forEach((item, i) => {
                    item[sortBy] = i + 1; // re order
                });
            }
        }
    }

    static add(items: Array, newItemTemplate: any, sortBy:string) {
        sortBy = sortBy || ArrayUtils._sortBy;
        let item = _.isObject(newItemTemplate) ? _.cloneDeep(newItemTemplate) : newItemTemplate;
        items.push(item);
        if (item[sortBy] !== undefined) {
            items.forEach((item, i) => {
                item[sortBy] = i + 1;  // re order
            });
        }
        return item;
    }

    static getNested(source: any, path: string, separator: string) {
        separator = separator || '.';
        return path.replace(/\[/g, separator).replace(/]/g, '').split(separator)
            .reduce((obj, property) => {
                return obj ? obj[property] : null;
            }, source);
    }

    static toDictionary(items: Array, key: string) {
        let dict = {};
        items.forEach(item => {
            dict[item[key]] = item;
        });
        return dict;
    }
}
