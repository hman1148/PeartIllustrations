{{/* charts/app/templates/_helpers.tpl */}}
{{- define "app.name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{- define "app.fullname" -}}
{{- printf "%s-%s" (include "app.name" .) .Release.Name | trunc 63 | trimSuffix "-" -}}
{{- end -}}