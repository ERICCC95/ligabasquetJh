'use strict';

angular.module('ligabasquetApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('estadisticasJugadorPartido', {
                parent: 'entity',
                url: '/estadisticasJugadorPartidos',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'ligabasquetApp.estadisticasJugadorPartido.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/estadisticasJugadorPartido/estadisticasJugadorPartidos.html',
                        controller: 'EstadisticasJugadorPartidoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('estadisticasJugadorPartido');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('estadisticasJugadorPartido.detail', {
                parent: 'entity',
                url: '/estadisticasJugadorPartido/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'ligabasquetApp.estadisticasJugadorPartido.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/estadisticasJugadorPartido/estadisticasJugadorPartido-detail.html',
                        controller: 'EstadisticasJugadorPartidoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('estadisticasJugadorPartido');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'EstadisticasJugadorPartido', function($stateParams, EstadisticasJugadorPartido) {
                        return EstadisticasJugadorPartido.get({id : $stateParams.id});
                    }]
                }
            })
            .state('estadisticasJugadorPartido.new', {
                parent: 'estadisticasJugadorPartido',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/estadisticasJugadorPartido/estadisticasJugadorPartido-dialog.html',
                        controller: 'EstadisticasJugadorPartidoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {canastas: null, asistencias: null, faltas: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('estadisticasJugadorPartido', null, { reload: true });
                    }, function() {
                        $state.go('estadisticasJugadorPartido');
                    })
                }]
            })
            .state('estadisticasJugadorPartido.edit', {
                parent: 'estadisticasJugadorPartido',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/estadisticasJugadorPartido/estadisticasJugadorPartido-dialog.html',
                        controller: 'EstadisticasJugadorPartidoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['EstadisticasJugadorPartido', function(EstadisticasJugadorPartido) {
                                return EstadisticasJugadorPartido.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('estadisticasJugadorPartido', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
