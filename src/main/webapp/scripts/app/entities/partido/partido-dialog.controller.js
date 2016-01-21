'use strict';

angular.module('ligabasquetApp').controller('PartidoDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Partido', 'Arbitro', 'Equipo', 'Estadisticasjugadorpartido',
        function($scope, $stateParams, $modalInstance, entity, Partido, Arbitro, Equipo, Estadisticasjugadorpartido) {

        $scope.partido = entity;
        $scope.arbitros = Arbitro.query();
        $scope.equipos = Equipo.query();
        $scope.estadisticasjugadorpartidos = Estadisticasjugadorpartido.query();
        $scope.load = function(id) {
            Partido.get({id : id}, function(result) {
                $scope.partido = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('ligabasquetApp:partidoUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.partido.id != null) {
                Partido.update($scope.partido, onSaveFinished);
            } else {
                Partido.save($scope.partido, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
