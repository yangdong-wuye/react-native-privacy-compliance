import { NativeModules } from 'react-native';

type PrivacyComplianceType = {
  multiply(a: number, b: number): Promise<number>;
};

const { PrivacyCompliance } = NativeModules;

export default PrivacyCompliance as PrivacyComplianceType;
