import React, { createContext, useEffect, useMemo, useReducer } from 'react';

export interface DecodedToken {
  id?: number;
  email?: string;
  firstName?: string;
  lastName?: string;
  phoneNumber?: string;
  roles?: string[];
  authorities?: string;
  sub?: string;
  iat?: number;
  exp?: number;
}

export interface User {
  id: number;
  email: string;
  firstName: string;
  lastName: string;
  phoneNumber: string;
  roles: string[];
  authorities: string;
}

export interface State {
  user: User | null;
  token: string | null;
  unreadMessages: number; 
}

export const ACTION_TYPE = {
  SET_USER: 'SET_USER',
  SET_TOKEN: 'SET_TOKEN',
  LOAD_STATE: 'LOAD_STATE',
  SET_UNREAD_MESSAGES: 'SET_UNREAD_MESSAGES', 

} as const;

type ActionType = (typeof ACTION_TYPE)[keyof typeof ACTION_TYPE];

export interface Action {
  type: ActionType;
  payload: any;
}

export interface GlobalContextProviderProps {
  children: React.ReactNode;
}
export interface AuthResponse {
  token: string;
}
const initialState: State = {
  user: null,
  token: null,
  unreadMessages: 0
};

const reducer = (state: State = initialState, action: Action): State => {
  switch (action.type) {
    case ACTION_TYPE.LOAD_STATE:
      return { ...state, ...action.payload };
    case ACTION_TYPE.SET_USER:
      return { ...state, user: action.payload };
    case ACTION_TYPE.SET_TOKEN:
      return { ...state, token: action.payload };
      case ACTION_TYPE.SET_UNREAD_MESSAGES:
        return {
          ...state,
          unreadMessages: action.payload
        };
    default:
      return state;
  }
};

export const GlobalContext = createContext<{
  state: State;
  dispatch: React.Dispatch<Action>;
}>({
  state: initialState,
  dispatch: () => null,
});

export const GlobalContextProvider: React.FC<GlobalContextProviderProps> = ({
  children,
}) => {
  const [state, dispatch] = useReducer(reducer, initialState);

  useEffect(() => {
    const storedState = sessionStorage.getItem('appState');
    if (storedState) {
      dispatch({
        type: ACTION_TYPE.LOAD_STATE,
        payload: JSON.parse(storedState),
      });
    }
  }, []);

  useEffect(() => {
    sessionStorage.setItem('appState', JSON.stringify(state));
  }, [state]);

  const contextValue = useMemo(() => ({ state, dispatch }), [state, dispatch]);

  return (
    <GlobalContext.Provider value={contextValue}>
      {children}
    </GlobalContext.Provider>
  );
};
