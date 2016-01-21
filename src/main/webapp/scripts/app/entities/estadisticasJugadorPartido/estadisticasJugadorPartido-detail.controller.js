'use strict';

angular.module('ligabasquetApp')
    .controller('EstadisticasJugadorPartidoDetailController', function ($scope, $rootScope, $stateParams, entity, EstadisticasJugadorPartido, Jugador, Partido) {
        $scope.estadisticasJugadorPartido = entity;
        $scope.load = function (id) {
            EstadisticasJugadorPartido.get({id: id}, function(result) {
                $scope.estadisticasJugadorPartido = result;
            });
        };
        $rootScope.$on('ligabasquetApp:estadisticasJugadorPartidoUpdate', function(event, result) {
            $scope.estadisticasJugadorPartido = result;
        });
    });
