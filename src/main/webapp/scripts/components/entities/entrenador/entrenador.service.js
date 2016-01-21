'use strict';

angular.module('ligabasquetApp')
    .factory('Entrenador', function ($resource, DateUtils) {
        return $resource('api/entrenadors/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
