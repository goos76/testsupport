import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IBoekingbestand, defaultValue } from 'app/shared/model/boekingbestand.model';

export const ACTION_TYPES = {
  FETCH_BOEKINGBESTAND_LIST: 'boekingbestand/FETCH_BOEKINGBESTAND_LIST',
  FETCH_BOEKINGBESTAND: 'boekingbestand/FETCH_BOEKINGBESTAND',
  CREATE_BOEKINGBESTAND: 'boekingbestand/CREATE_BOEKINGBESTAND',
  UPDATE_BOEKINGBESTAND: 'boekingbestand/UPDATE_BOEKINGBESTAND',
  DELETE_BOEKINGBESTAND: 'boekingbestand/DELETE_BOEKINGBESTAND',
  RESET: 'boekingbestand/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IBoekingbestand>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type BoekingbestandState = Readonly<typeof initialState>;

// Reducer

export default (state: BoekingbestandState = initialState, action): BoekingbestandState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_BOEKINGBESTAND_LIST):
    case REQUEST(ACTION_TYPES.FETCH_BOEKINGBESTAND):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_BOEKINGBESTAND):
    case REQUEST(ACTION_TYPES.UPDATE_BOEKINGBESTAND):
    case REQUEST(ACTION_TYPES.DELETE_BOEKINGBESTAND):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_BOEKINGBESTAND_LIST):
    case FAILURE(ACTION_TYPES.FETCH_BOEKINGBESTAND):
    case FAILURE(ACTION_TYPES.CREATE_BOEKINGBESTAND):
    case FAILURE(ACTION_TYPES.UPDATE_BOEKINGBESTAND):
    case FAILURE(ACTION_TYPES.DELETE_BOEKINGBESTAND):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_BOEKINGBESTAND_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_BOEKINGBESTAND):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_BOEKINGBESTAND):
    case SUCCESS(ACTION_TYPES.UPDATE_BOEKINGBESTAND):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_BOEKINGBESTAND):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/boekingbestands';

// Actions

export const getEntities: ICrudGetAllAction<IBoekingbestand> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_BOEKINGBESTAND_LIST,
    payload: axios.get<IBoekingbestand>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IBoekingbestand> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_BOEKINGBESTAND,
    payload: axios.get<IBoekingbestand>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IBoekingbestand> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_BOEKINGBESTAND,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IBoekingbestand> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_BOEKINGBESTAND,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IBoekingbestand> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_BOEKINGBESTAND,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
