import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './boekingbestand.reducer';
import { IBoekingbestand } from 'app/shared/model/boekingbestand.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IBoekingbestandUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BoekingbestandUpdate = (props: IBoekingbestandUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { boekingbestandEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/boekingbestand' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...boekingbestandEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="testsupportApp.boekingbestand.home.createOrEditLabel">Create or edit a Boekingbestand</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : boekingbestandEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="boekingbestand-id">ID</Label>
                  <AvInput id="boekingbestand-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="kenmerkLabel" for="boekingbestand-kenmerk">
                  Kenmerk
                </Label>
                <AvField id="boekingbestand-kenmerk" type="text" name="kenmerk" />
              </AvGroup>
              <AvGroup>
                <Label id="labelLabel" for="boekingbestand-label">
                  Label
                </Label>
                <AvInput
                  id="boekingbestand-label"
                  type="select"
                  className="form-control"
                  name="label"
                  value={(!isNew && boekingbestandEntity.label) || 'CZ'}
                >
                  <option value="CZ">CZ</option>
                  <option value="OHRA">OHRA</option>
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/boekingbestand" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  boekingbestandEntity: storeState.boekingbestand.entity,
  loading: storeState.boekingbestand.loading,
  updating: storeState.boekingbestand.updating,
  updateSuccess: storeState.boekingbestand.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BoekingbestandUpdate);
