import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IBoekingbestand } from 'app/shared/model/boekingbestand.model';
import { getEntities as getBoekingbestands } from 'app/entities/boekingbestand/boekingbestand.reducer';
import { getEntity, updateEntity, createEntity, reset } from './record.reducer';
import { IRecord } from 'app/shared/model/record.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IRecordUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const RecordUpdate = (props: IRecordUpdateProps) => {
  const [boekingbestandId, setBoekingbestandId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { recordEntity, boekingbestands, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/record');
  };

  useEffect(() => {
    if (!isNew) {
      props.getEntity(props.match.params.id);
    }

    props.getBoekingbestands();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...recordEntity,
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
          <h2 id="testsupportApp.record.home.createOrEditLabel">Create or edit a Record</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : recordEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="record-id">ID</Label>
                  <AvInput id="record-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="debiteurLabel" for="record-debiteur">
                  Debiteur
                </Label>
                <AvField id="record-debiteur" type="text" name="debiteur" />
              </AvGroup>
              <AvGroup>
                <Label id="veroorzakerLabel" for="record-veroorzaker">
                  Veroorzaker
                </Label>
                <AvField id="record-veroorzaker" type="text" name="veroorzaker" />
              </AvGroup>
              <AvGroup>
                <Label id="overeenkomstLabel" for="record-overeenkomst">
                  Overeenkomst
                </Label>
                <AvField id="record-overeenkomst" type="text" name="overeenkomst" />
              </AvGroup>
              <AvGroup>
                <Label id="datumIngangLabel" for="record-datumIngang">
                  Datum Ingang
                </Label>
                <AvField id="record-datumIngang" type="text" name="datumIngang" />
              </AvGroup>
              <AvGroup>
                <Label id="datumEindeLabel" for="record-datumEinde">
                  Datum Einde
                </Label>
                <AvField id="record-datumEinde" type="text" name="datumEinde" />
              </AvGroup>
              <AvGroup>
                <Label for="record-boekingbestand">Boekingbestand</Label>
                <AvInput id="record-boekingbestand" type="select" className="form-control" name="boekingbestand.id">
                  <option value="" key="0" />
                  {boekingbestands
                    ? boekingbestands.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/record" replace color="info">
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
  boekingbestands: storeState.boekingbestand.entities,
  recordEntity: storeState.record.entity,
  loading: storeState.record.loading,
  updating: storeState.record.updating,
  updateSuccess: storeState.record.updateSuccess,
});

const mapDispatchToProps = {
  getBoekingbestands,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(RecordUpdate);
