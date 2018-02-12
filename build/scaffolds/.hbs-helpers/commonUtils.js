var util = require('util');

module.exports = {
    currentYear: function() {
        return (new Date()).getFullYear();
    },

    // handlebars has limited character escaping capabilities so these are
    // needed when we need to wrap something in {}'s without whitespace on the inside
    // example:  {{leftBrace}}{{foo}}{{rightBrace}} generates {valueOfFoo}
    leftBrace: function() { return '{'; },
    rightBrace: function() { return '}'; },

    // use {{debug this}} to log the current handlebars context
    debug: function(value, depth) {
        console.log(util.inspect(value, true, depth || 2, true));
    },

    compare: function (lvalue, operator, rvalue, options) {

        var operators, result;

        if (arguments.length < 3) {
            throw new Error("Handlerbars Helper 'compare' needs 2 parameters");
        }

        if (options === undefined) {
            options = rvalue;
            rvalue = operator;
            operator = "===";
        }

        operators = {
            '==': function (l, r) { return l == r; },
            '===': function (l, r) { return l === r; },
            '!=': function (l, r) { return l != r; },
            '!==': function (l, r) { return l !== r; },
            '<': function (l, r) { return l < r; },
            '>': function (l, r) { return l > r; },
            '<=': function (l, r) { return l <= r; },
            '>=': function (l, r) { return l >= r; },
            'typeof': function (l, r) { return typeof l == r; }
        };

        if (!operators[operator]) {
            throw new Error("Handlerbars Helper 'compare' doesn't know the operator " + operator);
        }

        result = operators[operator](lvalue, rvalue);

        if (result) {
            return options.fn(this);
        } else {
            return options.inverse(this);
        }
    }
};
