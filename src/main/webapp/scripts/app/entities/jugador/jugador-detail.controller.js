'use strict';

angular.module('ligabasquetApp')
    .controller('JugadorDetailController', function ($scope, $rootScope, $stateParams, entity, Jugador, Equipo, Estadisticasjugadorpartido) {
        $scope.jugador = entity;
        $scope.load = function (id) {
            Jugador.get({id: id}, function(result) {
                $scope.jugador = result;
            });
        };
        $rootScope.$on('ligabasquetApp:jugadorUpdate', function(event, result) {
            $scope.jugador = result;
        });
    });
