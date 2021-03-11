import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Boekingbestand from './boekingbestand';
import BoekingbestandDetail from './boekingbestand-detail';
import BoekingbestandUpdate from './boekingbestand-update';
import BoekingbestandDeleteDialog from './boekingbestand-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BoekingbestandUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BoekingbestandUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BoekingbestandDetail} />
      <ErrorBoundaryRoute path={match.url} component={Boekingbestand} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BoekingbestandDeleteDialog} />
  </>
);

export default Routes;
