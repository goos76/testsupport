import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './record.reducer';
import { IRecord } from 'app/shared/model/record.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRecordDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const RecordDetail = (props: IRecordDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { recordEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          Record [<b>{recordEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="debiteur">Debiteur</span>
          </dt>
          <dd>{recordEntity.debiteur}</dd>
          <dt>
            <span id="veroorzaker">Veroorzaker</span>
          </dt>
          <dd>{recordEntity.veroorzaker}</dd>
          <dt>
            <span id="overeenkomst">Overeenkomst</span>
          </dt>
          <dd>{recordEntity.overeenkomst}</dd>
          <dt>
            <span id="datumIngang">Datum Ingang</span>
          </dt>
          <dd>{recordEntity.datumIngang}</dd>
          <dt>
            <span id="datumEinde">Datum Einde</span>
          </dt>
          <dd>{recordEntity.datumEinde}</dd>
          <dt>Boekingbestand</dt>
          <dd>{recordEntity.boekingbestand ? recordEntity.boekingbestand.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/record" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/record/${recordEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ record }: IRootState) => ({
  recordEntity: record.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(RecordDetail);
